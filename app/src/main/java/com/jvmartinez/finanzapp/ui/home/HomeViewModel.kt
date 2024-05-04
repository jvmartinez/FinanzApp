package com.jvmartinez.finanzapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.model.TransactionView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private var balance by mutableStateOf(BalanceView())
        private set

    init {
        getBalance()
    }

    private fun getBalance() {
        // TODO get balance from server
        balance = BalanceView(
            "$1000.00",
            transactions = listOf(
                TransactionView(
                    id = "0x0001",
                    amount = "$100.00",
                    date = "2022-01-01"
                )
            )
        )
    }

    fun onBalance() = balance
}