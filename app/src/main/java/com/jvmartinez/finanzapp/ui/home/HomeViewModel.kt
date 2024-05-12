package com.jvmartinez.finanzapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.Balance
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toBalanceView
import com.jvmartinez.finanzapp.core.model.toTransactionViews
import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.IRepositoryBalance
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.model.BalanceView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val balanceRepository: IRepositoryBalance,
    private val repositoryDB: DataBaseRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val loadingData = MutableLiveData<StatusData<BalanceView>>(StatusData.Empty)


    fun getBalance() {
        viewModelScope.launch {
            Log.d("HomeViewModel", "getBalance: ${preferencesRepository.getUserKey()}")
            loadingData.postValue(StatusData.Loading)
            generateCombineSelectDB().catch {
                processRemote()
            }.collect { (balance, transaction) ->
                updateData(balance, transaction)
            }
        }
    }

    private suspend fun processRemote() {
        generateCombineApiResponse().catch {
            loadingData.postValue(StatusData.Error(it.message.toString()))
        }.collect {
            it.first.let { balance ->
                repositoryDB.saveBalance(balance)
                it.second.let { transaction ->
                    if (transaction.isNotEmpty()) {
                        transaction.forEach { trans ->
                            repositoryDB.saveTransaction(trans)
                        }
                    }
                    updateData(it.first, it.second)
                }
            }
        }
    }

    private fun updateData(balance: Balance, transaction: List<Transaction>) {
        loadingData.postValue(
            StatusData.Success(
                balance.toBalanceView(transaction.toTransactionViews())
            )
        )
    }

    private suspend fun generateCombineApiResponse(): Flow<Pair<Balance, List<Transaction>>> {
        return flow {
            val response = balanceRepository.getBalance()
                .zip(balanceRepository.findByUserTransaction()) { balance, transaction ->
                    Pair(balance.data, transaction.data)
                }
            response.collect {
                emit(it)
            }
        }

    }

    private suspend fun generateCombineSelectDB(): Flow<Pair<Balance, List<Transaction>>> {
        return flow {
            lateinit var balance: Balance
            repositoryDB.getBalance().collect {
                balance = it
            }
            lateinit var transaction: List<Transaction>
            repositoryDB.getTransactions().collect {
                transaction = it
            }
            emit(Pair(balance, transaction))
        }
    }

    fun onLoadingData(): LiveData<StatusData<BalanceView>> = loadingData
    fun onDismissDialog() {
        loadingData.value = StatusData.Empty
    }
}
