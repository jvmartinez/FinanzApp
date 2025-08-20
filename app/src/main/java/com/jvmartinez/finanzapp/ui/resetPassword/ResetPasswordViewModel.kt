package com.jvmartinez.finanzapp.ui.resetPassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.repository.remote.login.LoginRepository
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.resetPassword.state.ResetPasswordIUState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val stateIU = MutableStateFlow(ResetPasswordIUState())

    fun updateState(
        email: String? = null,
        toggleButton: Boolean? = null,
        loadingData: StatusData<Boolean>? = null
    ) {
        stateIU.update {
            it.copy(
                email = email ?: it.email,
                toggleButton = toggleButton ?: it.toggleButton,
                loadingData = loadingData ?: it.loadingData
            )
        }
    }
    private fun setEmail(email: String) {
        updateState(
            email = email
        )
    }

    private fun emailChanged(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun changeInput(email: String) {
        setEmail(email)
        updateState(
            toggleButton = emailChanged(email)
        )
    }

    fun onResetPassword() {
        viewModelScope.launch {

            stateIU.value.email.let { value ->
                loginRepository.resetPassword(value).catch { error ->
                    updateState(
                        loadingData = StatusData.Error(error = error.stackTraceToString())
                    )
                }.collect {responseData ->
                    updateState(
                        loadingData = StatusData.Success(responseData.data)
                    )
                }
            }
        }
    }

    fun onStateIU(): StateFlow<ResetPasswordIUState> = stateIU
}