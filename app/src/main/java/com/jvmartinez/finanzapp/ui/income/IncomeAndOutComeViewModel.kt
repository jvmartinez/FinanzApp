package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class IncomeAndOutComeViewModel @Inject constructor(): ViewModel() {

    private val date = MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun processDate(selectedDateMillis: Long?) {
        selectedDateMillis?.let { dateMillis ->
            val selectedDate = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("UTC")).toLocalDate()
            val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            this.date.value = formattedDate
        }
    }

    fun onDate() : LiveData<String> = date
}