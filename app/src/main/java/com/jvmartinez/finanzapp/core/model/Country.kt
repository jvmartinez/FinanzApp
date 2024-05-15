package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country(
    @JsonProperty("name") val name: String,
    @JsonProperty("code") val code: String,
    @JsonProperty("currency") val currency: String,
    @JsonProperty("symbol") val symbol: String
)
