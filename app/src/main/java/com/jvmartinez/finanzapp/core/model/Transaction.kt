package com.jvmartinez.finanzapp.core.model

data class Transaction(
    val id: String,
    val amount: Double,
    val date: String,
    val description: String
)
