package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.finanzapp.core.entity.EntityBalance
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.model.TransactionView
import com.jvmartinez.finanzapp.utils.Utils.formatAmount

data class Balance(
    @JsonProperty("id") val id: String,
    @JsonProperty("balance") var balance: Double?,
    @JsonProperty("income") var income: Double?,
    @JsonProperty("outcome") var outcome: Double?,
    @JsonProperty("create_at") val createAt: String?,
    @JsonProperty("update_at") val updateAt: String?
)

fun Balance.toBalanceView(transactionView: List<TransactionView>, symbol: String): BalanceView {
    return BalanceView(
        balance = formatAmount(balance ?: 0.0, symbol),
        income = formatAmount(income ?: 0.0, symbol),
        outcome = formatAmount(outcome ?: 0.0, symbol),
        transactions = transactionView,
        createAt = createAt,
        updateAt = updateAt
    )
}

fun Balance.toEntity(user: String, isSynchronized: Boolean = false): EntityBalance {
    return EntityBalance(
        id = user,
        balance = balance ?: 0.0,
        income = income ?: 0.0,
        outcome = outcome ?: 0.0,
        createAt = createAt.orEmpty(),
        updateAt = updateAt.orEmpty(),
        isSynchronized = isSynchronized
    )
}
