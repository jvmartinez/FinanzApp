package com.jvmartinez.finanzapp.ui.credential

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.UserModel
import com.jvmartinez.finanzapp.core.repository.local.perferences.IPreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.login.ILoginRepository
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.credential.state.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
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

    private val loginState = MutableStateFlow<LoginUIState>(LoginUIState())

    fun updateState(
        name: String? = null,
        email: String? = null,
        password: String? = null,
        toggleButton: Boolean? = null,
        loadingData: StatusData<Boolean>? = null,
        isValidPassword: Boolean? = null,
        isValidEmail: Boolean? = null,
        isValidName: Boolean? = null
    ) {
        loginState.update {
            it.copy(
                name = name ?: it.name,
                email = email ?: it.email,
                password = password ?: it.password,
                toggleButton = toggleButton ?: it.toggleButton,
                loadingData = loadingData ?: it.loadingData,
                isValidPassword = isValidPassword ?: it.isValidPassword,
                isValidEmail = isValidEmail ?: it.isValidEmail,
                isValidName = isValidName ?: it.isValidName
            )
        }
    }
    fun onChanceTextFieldLogin(email: String, password: String) {
       updateState(
           email = email,
           password = password,
           isValidEmail = isValidEmail() && isValidPassword()
       )
        if (password.length > 1) {
            updateState(
                isValidPassword = isValidPassword() && loginState.value.password.length > 1
            )
        }
        validToggleButton()
    }

    fun onChanceTextFieldSignUp(name: String, email: String, password: String) {
        updateState(
            name = name,
            email = email,
            password = password,
            isValidEmail = isValidEmail(),
            isValidName = isValidName()
        )
        if (password.length > 1) {
            updateState(
                isValidPassword = isValidPassword() && loginState.value.password.length > 1
            )
        }
        validToggleButtonSignUp()
    }

    private fun validToggleButton() {
        updateState(
            toggleButton = isValidEmail() && isValidPassword()
        )
    }

    private fun validToggleButtonSignUp() {
        updateState(
            toggleButton = isValidEmail() && isValidPassword() && isValidName()
        )
    }

    private fun isValidName(): Boolean =
        (loginState.value.name.length) > ZERO_LENGTH

    private fun isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(loginState.value.email).matches()

    private fun isValidPassword(): Boolean {
        val regexPattern =
            "(?=.*[!@#\$%^&*(),.?\":{}|<>])[A-Z][a-zA-Z0-9!@#\$%^&*(),.?\":{}|<>]{5,}".toRegex()
        return loginState.value.password.matches(regexPattern)
    }

    fun onLogin() {
        viewModelScope.launch {
            updateState(
                loadingData = StatusData.Loading
            )
            repository.signIn(loginState.value.email, loginState.value.password).catch {
                updateState(
                    loadingData = StatusData.Error(it.message.orEmpty())
                )
            }.collect {
                preferencesRepository.setUserKey(it.data.id.orEmpty())
                preferencesRepository.setUserToken(it.data.token.orEmpty())
                preferencesRepository.setUserName(it.data.name.orEmpty())
                preferencesRepository.setSymbolKey("$")
                preferencesRepository.setCurrencyKey("USD")
                updateState(
                    loadingData = StatusData.Success(it.code == OK_CODE)
                )
            }
        }
    }

    fun onSignUp() {
        viewModelScope.launch {
            updateState(
                loadingData = StatusData.Loading
            )
            repository.signUp(
                UserModel(loginState.value.name, loginState.value.email, loginState.value.password.orEmpty())
            ).catch {
                updateState(
                    loadingData = StatusData.Error(it.message.orEmpty())
                )
            }.collect {
                preferencesRepository.setUserKey(it.data.id.orEmpty())
                preferencesRepository.setUserName(it.data.name.orEmpty())
                preferencesRepository.setUserToken(it.data.token.orEmpty())
                preferencesRepository.setSymbolKey("$")
                preferencesRepository.setCurrencyKey("USD")
                updateState(
                    loadingData = StatusData.Success(it.code == OK_CODE)
                )
            }
        }
    }


    fun onClearField() {
        loginState.value = LoginUIState()
    }


    fun onDismissDialog() {
        updateState(
            loadingData = StatusData.Empty
        )
    }

    fun onLoginUIState(): StateFlow<LoginUIState> = loginState
}
