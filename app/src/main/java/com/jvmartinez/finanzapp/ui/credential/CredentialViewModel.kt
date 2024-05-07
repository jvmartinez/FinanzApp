package com.jvmartinez.finanzapp.ui.credential

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.UserModel
import com.jvmartinez.finanzapp.core.repository.local.perferences.IPreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.login.ILoginRepository
import com.jvmartinez.finanzapp.ui.base.StatusData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ZERO_LENGTH = 0
private const val MAXIMUM_PASSWORD_LENGTH = 6
private const val OK_CODE = 200

@HiltViewModel
class CredentialViewModel @Inject constructor(
    private val repository: ILoginRepository,
    private val preferencesRepository: IPreferencesRepository
) : ViewModel() {

    private val name = MutableLiveData("")

    private val email = MutableLiveData("")

    private val password = MutableLiveData("")

    private val toggleButton = MutableLiveData(false)

    private val loadingData = MutableLiveData<StatusData<Boolean>>(StatusData.Empty)

    fun onChanceTextFieldLogin(email: String, password: String) {
        this.email.value = email
        this.password.value = password
        validToggleButton()
    }

    fun onChanceTextFieldSignUp(name: String, email: String, password: String) {
        this.name.value = name
        this.email.value = email
        this.password.value = password
        validToggleButtonSignUp()
    }

    private fun validToggleButton() {
        toggleButton.postValue(isValidEmail() && isValidPassword())
    }

    private fun validToggleButtonSignUp() {
        toggleButton.postValue(isValidEmail() && isValidPassword() && isValidName())
    }

    private fun isValidName(): Boolean =
        (name.value.orEmpty().length) > ZERO_LENGTH

    private fun isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email.value.orEmpty()).matches()

    private fun isValidPassword(): Boolean =
        (password.value.orEmpty().length) >= MAXIMUM_PASSWORD_LENGTH

    fun onLogin() {
        viewModelScope.launch {
            loadingData.value = StatusData.Loading
            repository.signIn(email.value.orEmpty(), password.value.orEmpty()).catch {
                loadingData.value = StatusData.Error(it.message.orEmpty())
            }.collect {
                preferencesRepository.setUserToken(it.data.token.orEmpty())
                preferencesRepository.setUserName(it.data.name.orEmpty())
                loadingData.value = StatusData.Success(it.code == OK_CODE)
            }
        }
    }

    fun onSignUp() {
        viewModelScope.launch {
            loadingData.value = StatusData.Loading
            repository.signUp(
                UserModel(name.value.orEmpty(), email.value.orEmpty(), password.value.orEmpty())
            ).catch {
                loadingData.value = StatusData.Error(it.message.orEmpty())
            }.collect {
                preferencesRepository.setUserName(it.data.name.orEmpty())
                preferencesRepository.setUserToken(it.data.token.orEmpty())
                loadingData.value = StatusData.Success(it.code == OK_CODE)
            }
        }
    }

    fun onClearField() {
        email.value = ""
        password.value = ""
        name.value = ""
    }

    fun onToggleButton(): LiveData<Boolean> = toggleButton

    fun onEmail(): LiveData<String> = email

    fun onPassword(): LiveData<String> = password

    fun onName(): LiveData<String> = name
    fun onLoadingData(): LiveData<StatusData<Boolean>> = loadingData
}
