package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Countries(
   @JsonProperty("countries") val countries: List<Country>
)
