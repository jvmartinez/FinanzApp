package com.jvmartinez.finanzapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toTransactionViews
import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.RepositoryBalance
import com.jvmartinez.finanzapp.ui.model.TransactionView
import com.jvmartinez.finanzapp.utils.Utils.getFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTransactionViewModel @Inject constructor(
    private val repository: RepositoryBalance,
    private val repositoryDB: DataBaseRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    private val transactions = MutableLiveData<List<TransactionView>>()
    private val dateStart = MutableLiveData<String>()
    private val dateEnd = MutableLiveData<String>()

    fun findTransactions() {
        viewModelScope.launch {
            repositoryDB.findAllByDates(
                onDateStart().value.orEmpty(),
                onDateEnd().value.orEmpty()
            ).collect {
                Log.d("DetailTransactionViewModel", it.toString())
                setTransactions(it, preferencesRepository.getSymbolKey().getOrNull() ?: "$")
            }
        }
    }

    private fun setTransactions(transactions: List<Transaction>, symbol: String = "$") {
        this.transactions.postValue(transactions.toTransactionViews(symbol))
    }

    fun onTransaction(): LiveData<List<TransactionView>> = transactions

    fun setDateStart(date: Long) {
        dateStart.value = getFormattedDate(date)
    }

    fun setDateEnd(date: Long) {
        dateEnd.value = getFormattedDate(date)
    }

    fun onDateStart(): LiveData<String> = dateStart

    fun onDateEnd(): LiveData<String> = dateEnd
}