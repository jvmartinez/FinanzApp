package com.jvmartinez.finanzapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.ui.base.StatusData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_OUT = 4500L

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val isValidateLogin = MutableLiveData<StatusData<Boolean>>()


    init {
        viewModelScope.launch {
            isValidateLogin.postValue(StatusData.Loading)
            delay(timeMillis = TIME_OUT)
            isValidateLogin.postValue(StatusData.Success(false))
        }
    }

    fun onValidateLogin(): LiveData<StatusData<Boolean>> = isValidateLogin
}
