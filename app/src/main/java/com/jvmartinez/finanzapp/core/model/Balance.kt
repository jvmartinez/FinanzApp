package com.jvmartinez.finanzapp.core.model

data class Balance(
    val balance: Double,
    val income: Double,
    val outcome: Double,
    val transactions: List<Transaction>
)
