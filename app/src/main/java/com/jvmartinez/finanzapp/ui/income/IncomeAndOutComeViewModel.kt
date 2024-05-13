package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.CategoryModel
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toEntity
import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.RepositoryBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val CODE_SUCCESS = 200
private const val SEND_LOT_SIZE = 5

@HiltViewModel
class IncomeAndOutComeViewModel @Inject constructor(
    private val repository: RepositoryBalance,
    private val repositoryDB: DataBaseRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val date = MutableLiveData<String>()
    private val typeTransaction = MutableLiveData<Int>()
    private val getCategories = MutableLiveData<MutableList<CategoryModel>>()
    private val description = MutableLiveData<String>()
    private val amount = MutableLiveData<Double>()
    private val iconTransaction = MutableLiveData<Int>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun processDate(selectedDateMillis: Long?) {
        selectedDateMillis?.let { dateMillis ->
            val selectedDate =
                Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("UTC")).toLocalDate()
            val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            this.date.value = formattedDate
        }
    }

    fun setTpeTransaction(category: CategoryModel) {
        this.typeTransaction.value = category.id

        val updateAll = getCategories().value?.map {
            if (it.id == category.id) {
                it.copy(selected = !it.selected)
            } else {
                it.copy(selected = false)
            }
        }
        updateAll?.let {
            setCategories(it)
        }
        this.iconTransaction.value = category.iconDrawable
    }

    fun onDate(): LiveData<String> = date

    private fun onTypeTransaction(): LiveData<Int> = typeTransaction

    fun getCategories(): LiveData<MutableList<CategoryModel>> = getCategories
    fun setCategories(categories: List<CategoryModel>) {
        getCategories.value = categories.toMutableList()
    }

    fun getDescription(): LiveData<String> = description

    fun getAmount(): LiveData<Double> = amount

    private fun getTypeIcon(): LiveData<Int> = iconTransaction


    fun save() {
        val transaction = Transaction(
            id = 0.0,
            amount = getAmount().value ?: 0.0,
            date = onDate().value.orEmpty(),
            description = getDescription().value.orEmpty(),
            type = onTypeTransaction().value ?: 0,
            typeIcon = getTypeIcon().value ?: 0,
        )
        viewModelScope.launch {
            repositoryDB.saveTransaction(transaction)
            repositoryDB.getTransactions().collect {
                if (it.isNotEmpty() && it.size >= SEND_LOT_SIZE) {
                    repository.createTransaction(it).collect { response ->
                        if (response.code == CODE_SUCCESS) {
                            response.data.forEach { transaction ->
                                repositoryDB.updateTransaction(
                                    transaction.toEntity(
                                        preferencesRepository.getUserKey().getOrNull().orEmpty(),
                                        synchronized = true
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onChangeSave(description: String, amount: Double, date: String) {
        this.description.value = description
        this.amount.value = amount
        this.date.value = date
    }
}