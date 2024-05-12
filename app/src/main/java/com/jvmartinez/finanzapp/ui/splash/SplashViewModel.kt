package com.jvmartinez.finanzapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.ui.base.StatusData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_OUT = 4500L

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: PreferencesRepository
): ViewModel() {

    private val statusData = MutableLiveData<StatusData<Boolean>>()

    init {
        viewModelScope.launch {
            val isValidToken =  repository.getUserToken().getOrNull().orEmpty()
            statusData.value = StatusData.Loading
            delay(timeMillis = TIME_OUT)
            statusData.value = StatusData.Success(isValidToken.isNotEmpty())
        }
    }

    fun onStatusData(): LiveData<StatusData<Boolean>> = statusData
}
