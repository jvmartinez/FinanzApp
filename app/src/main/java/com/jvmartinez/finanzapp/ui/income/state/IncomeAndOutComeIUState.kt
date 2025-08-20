package com.jvmartinez.finanzapp.ui.income.state

import com.jvmartinez.finanzapp.core.model.CategoryModel

data class IncomeAndOutComeIUState(
    val date: String = "",
    val description: String = "",
    val amount: String = "0.0",
    val toggleButton: Boolean = false,
    val iconTransaction: Int = 0,
    val listCategory: List<CategoryModel> = mutableListOf(),
    val listOutComeCategory: List<CategoryModel> = mutableListOf(),
)
