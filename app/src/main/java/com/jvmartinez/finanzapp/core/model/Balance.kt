package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.finanzapp.core.entity.EntityBalance
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.model.TransactionView

data class Balance(
    @JsonProperty("id") val id: String,
    @JsonProperty("balance") val balance: Double?,
    @JsonProperty("income") val income: Double?,
    @JsonProperty("outcome") val outcome: Double?,
    @JsonProperty("create_at") val createAt: String?,
    @JsonProperty("update_at") val updateAt: String?
)

fun Balance.toBalanceView(transactionView: List<TransactionView>): BalanceView {
    return BalanceView(
        balance = balance.toString(),
        income = income.toString(),
        outcome = outcome.toString(),
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
