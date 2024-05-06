package com.jvmartinez.finanzapp.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.repository.remote.balance.IRepositoryBalance
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.model.TransactionView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val balanceRepository: IRepositoryBalance
) : ViewModel() {
    private var balance by mutableStateOf(BalanceView())
        private set

    init {
        getBalance()
    }

    private fun getBalance() {
        viewModelScope.launch {
            balanceRepository.getBalance().catch {
                Log.e("HomeViewModel", it.message.toString())
            }.collect {
                it.data.let { balanceData ->
                    balance = BalanceView(
                        balance = balanceData.balance.toString(),
                        transactions = balanceData.transactions.map { transactions ->
                            TransactionView(
                                id = transactions.id,
                                amount = transactions.amount.toString(),
                                date = transactions.date,
                                description = transactions.description
                            )
                        },
                        income = balanceData.income.toString(),
                        outcome = balanceData.outcome.toString(),
                        createAt = balanceData.createAt,
                        updateAt = balanceData.updateAt
                    )
                }
            }
        }
    }

    fun onBalance() = balance
}
