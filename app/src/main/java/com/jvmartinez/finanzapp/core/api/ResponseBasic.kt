package com.jvmartinez.finanzapp.core.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseBasic<out T>(
    @JsonProperty("code") val code: Int,
    @JsonProperty("message") val message: String,
    @JsonProperty("data") val data: T
)
