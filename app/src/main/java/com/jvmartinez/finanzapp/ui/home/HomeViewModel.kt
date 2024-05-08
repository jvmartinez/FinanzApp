package com.jvmartinez.finanzapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.toBalanceView
import com.jvmartinez.finanzapp.core.repository.remote.balance.IRepositoryBalance
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.model.BalanceView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val balanceRepository: IRepositoryBalance
) : ViewModel() {

    private val loadingData = MutableLiveData<StatusData<BalanceView>>(StatusData.Empty)

    fun getBalance() {
        viewModelScope.launch {
            loadingData.postValue(StatusData.Loading)
            balanceRepository.getBalance().catch {
                loadingData.postValue(StatusData.Error(it.message.toString()))
            }.collect {
                it.data.let { balanceData ->
                    loadingData.postValue(StatusData.Success(balanceData.toBalanceView()))
                }

            }
        }
    }

    fun onLoadingData(): LiveData<StatusData<BalanceView>> = loadingData
    fun onDismissDialog() {
        loadingData.value = StatusData.Empty
    }
}
