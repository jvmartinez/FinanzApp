package com.jvmartinez.finanzapp.ui.credential.state

import com.jvmartinez.finanzapp.ui.base.StatusData

data class LoginUIState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val toggleButton: Boolean = false,
    val loadingData: StatusData<Boolean> = StatusData.Empty,
    val isValidPassword: Boolean = true,
    val isValidEmail: Boolean = true,
    val isValidName: Boolean = true
)
