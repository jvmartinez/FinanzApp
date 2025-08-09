package com.jvmartinez.finanzapp.ui.resetPassword.state

import com.jvmartinez.finanzapp.ui.base.StatusData

data class ResetPasswordIUState(
    val email: String = "",
    val toggleButton: Boolean = false,
    val loadingData: StatusData<Boolean> = StatusData.Empty
)
