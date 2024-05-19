package com.jvmartinez.finanzapp.ui.resetPassword

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.repository.remote.login.LoginRepository
import com.jvmartinez.finanzapp.ui.base.StatusData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {
    private val email = MutableLiveData("")
    private val toggleButton = MutableLiveData(false)
    private val response =  MutableLiveData<StatusData<Boolean>>(StatusData.Empty)

    private fun setEmail(email: String) {
        this.email.value = email
    }

    fun onEmail(): LiveData<String> = email

    private fun emailChanged(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun changeInput(email: String) {
        setEmail(email)
        toggleButton.value = emailChanged(email)
    }

    fun onToggleButton(): LiveData<Boolean> = toggleButton

    fun onResetPassword() {
        viewModelScope.launch {
            email.value?.let {value ->
                loginRepository.resetPassword(value).catch { error ->
                    response.value = StatusData.Error(error = error.stackTraceToString())
                }.collect {responseData ->
                    response.value = StatusData.Success(responseData.data)
                }
            }
        }
    }

    fun onDismissDialog() {
        response.value = StatusData.Empty
    }
    fun onResponse(): LiveData<StatusData<Boolean>> = response
}