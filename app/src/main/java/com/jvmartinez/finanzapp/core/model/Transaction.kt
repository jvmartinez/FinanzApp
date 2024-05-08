package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.finanzapp.ui.model.TransactionView

data class Transaction(
    @JsonProperty("id") val id: Double,
    @JsonProperty("amount") val amount: Double,
    @JsonProperty("date") val date: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("type") val type: Int,
    @JsonProperty("type_icon") val typeIcon: Int
)

fun Transaction.toTransactionView(): TransactionView {
    return TransactionView(
        id = id,
        amount = amount.toString(),
        date = date,
        description = description,
        type = type,
        typeIcon = typeIcon
    )
}

fun List<Transaction>.toTransactionViews(): List<TransactionView> {
    return map { it.toTransactionView() }
}
