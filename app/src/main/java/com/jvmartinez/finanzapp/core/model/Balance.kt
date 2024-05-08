package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmartinez.finanzapp.ui.model.BalanceView

data class Balance(
    @JsonProperty("id") val id: String?,
    @JsonProperty("balance") val balance: Double?,
    @JsonProperty("income") val income: Double?,
    @JsonProperty("outcome") val outcome: Double?,
    @JsonProperty("transactions") val transactions: List<Transaction>?,
    @JsonProperty("create_at") val createAt: String?,
    @JsonProperty("update_at") val updateAt: String?
)

fun Balance.toBalanceView(): BalanceView {
    return BalanceView(
        balance = balance.toString(),
        income = income.toString(),
        outcome = outcome.toString(),
        transactions = transactions?.toTransactionViews().orEmpty(),
        createAt = createAt,
        updateAt = updateAt
    )
}
