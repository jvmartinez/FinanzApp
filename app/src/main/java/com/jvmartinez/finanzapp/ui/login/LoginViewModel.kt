package com.jvmartinez.finanzapp.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val email = MutableLiveData("")
    private val password = MutableLiveData("")
    private val toggleButton = MutableLiveData(false)

    fun onChangedEmail(email: String) {
        this.email.postValue(email)
        validToggleButton()
    }

    fun onChangedPassword(password: String) {
        this.password.postValue(password)
        validToggleButton()
    }

    private fun validToggleButton() {
        toggleButton.postValue(isValidEmail() && isValidPassword())
    }

    private fun isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email.value.orEmpty()).matches()

    private fun isValidPassword(): Boolean = (password.value.orEmpty().length) >= 6

    fun onToggleButton(): LiveData<Boolean> = toggleButton

    fun onEmail(): LiveData<String> = email

    fun onPassword(): LiveData<String> = password
}