package com.jvmartinez.finanzapp.ui.model

class BalanceView(
    val balance: String? = "$0.00",
    val income: String? = "$0.00",
    val outcome: String? = "$0.00",
    val transactions: List<TransactionView> = emptyList(),
    val createAt: String? = "",
    val updateAt: String? = ""
)
