package com.jvmartinez.finanzapp.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserModel(
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("email") val email: String? = null,
    @JsonProperty("password") val password: String? = null,
    @JsonProperty("token") val token: String? = null
)
