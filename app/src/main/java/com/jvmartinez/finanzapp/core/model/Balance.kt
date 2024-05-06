package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Balance(
    val balance: Double,
    val income: Double,
    val outcome: Double,
    val transactions: List<Transaction>,
    @JsonProperty("create_at") val createAt: String? = "",
    @JsonProperty("update_at") val updateAt: String? = ""
)
