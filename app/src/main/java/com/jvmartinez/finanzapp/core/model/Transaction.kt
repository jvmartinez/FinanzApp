package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.finanzapp.core.entity.EntityTransaction
import com.jvmartinez.finanzapp.ui.model.TransactionView
import com.jvmartinez.finanzapp.utils.Utils.formatAmount

data class Transaction(
    @JsonProperty("id") val id: Double,
    @JsonProperty("amount") val amount: Double,
    @JsonProperty("date") val date: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("type") val type: Int,
    @JsonProperty("type_icon") val typeIcon: Int,
)

fun Transaction.toEntity(user: String, synchronized: Boolean = false): EntityTransaction {
    return EntityTransaction(
        id = id.toInt(),
        amount = amount,
        date = date,
        description = description,
        type = type,
        typeIcon = typeIcon,
        userKey = user,
        isSynchronized = synchronized
    )
}


fun List<Transaction>.toEntity(user: String): List<EntityTransaction> {
    return map { it.toEntity(user) }
}

fun Transaction.toTransactionView(symbol: String): TransactionView {
    return TransactionView(
        id = id,
        amount = formatAmount(amount, symbol),
        date = date,
        description = description,
        type = type,
        typeIcon = typeIcon
    )
}

fun List<Transaction>.toTransactionViews(symbol: String): List<TransactionView> {
    return map { it.toTransactionView(symbol) }
}
