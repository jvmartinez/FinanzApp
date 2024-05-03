package com.jvmartinez.finanzapp.core.model

import java.util.Calendar

data class Transaction(
    val id: String,
    val amount: Double,
    val date: Calendar
)
