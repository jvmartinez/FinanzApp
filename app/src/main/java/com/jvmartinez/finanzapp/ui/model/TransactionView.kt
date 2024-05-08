package com.jvmartinez.finanzapp.ui.model

class TransactionView(
    val id: Double = 0.0,
    val amount: String = "",
    val date: String = "",
    val description: String = "Default",
    val type: Int = 0,
    val typeIcon: Int = 0
)
