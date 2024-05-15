package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmartinez.finanzapp.core.model.Balance
import com.jvmartinez.finanzapp.core.model.CategoryModel
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toEntity
import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.RepositoryBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val CODE_SUCCESS = 200
private const val SEND_LOT_SIZE = 5

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class IncomeAndOutComeViewModel @Inject constructor(
    private val repository: RepositoryBalance,
    private val repositoryDB: DataBaseRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val date = MutableLiveData<String>()
    private val getCategories = MutableLiveData<MutableList<CategoryModel>>()
    private val getOutComeCategories = MutableLiveData<MutableList<CategoryModel>>()
    private val description = MutableLiveData<String>()
    private val amount = MutableLiveData<String>()
    private val iconTransaction = MutableLiveData<Int>()
    private val toggleButton = MutableLiveData(false)

    @RequiresApi(Build.VERSION_CODES.O)
    fun processDate(selectedDateMillis: Long?) {
        selectedDateMillis?.let { dateMillis ->
            val selectedDate =
                Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("UTC")).toLocalDate()
            this.date.value = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        } ?: {
            this.date.value = ""
        }
        validToggleButton()
    }


    fun setTpeTransaction(category: CategoryModel, type: Int) {
        if (type == 1) {
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
        } else {
            val updateAll = getOutComeCategories().value?.map {
                if (it.id == category.id) {
                    it.copy(selected = !it.selected)
                } else {
                    it.copy(selected = false)
                }
            }
            updateAll?.let {
                setOutComeCategories(it)
            }
        }

        this.iconTransaction.value = category.iconDrawable
        validToggleButton()
    }

    fun onDate(): LiveData<String> = date

    fun getCategories(): LiveData<MutableList<CategoryModel>> = getCategories

    fun setCategories(categories: List<CategoryModel>) {
        getCategories.value = categories.toMutableList()
    }

    fun getOutComeCategories(): LiveData<MutableList<CategoryModel>> = getOutComeCategories

    fun setOutComeCategories(categories: List<CategoryModel>) {
        getOutComeCategories.value = categories.toMutableList()
    }

    fun getDescription(): LiveData<String> = description

    fun getAmount(): LiveData<String> = amount

    private fun getTypeIcon(): LiveData<Int> = iconTransaction


    fun save(type: Int) {
        val transaction = Transaction(
            id = 0.0,
            amount = getAmount().value?.toDouble() ?: 0.0,
            date = onDate().value.orEmpty(),
            description = getDescription().value.orEmpty(),
            type = type,
            typeIcon = getTypeIcon().value ?: 0,
        )
        lateinit var balance: Balance
        viewModelScope.launch {
            repositoryDB.getBalance().collect { balanceDB ->
                balance = balanceDB
                balance.balance = (balance.balance ?: 0.0) + transaction.amount
                balance.income = (balance.income ?: 0.0) + transaction.amount
            }
            repositoryDB.updateBalance(
                balance.toEntity(
                    preferencesRepository.getUserKey().getOrNull().orEmpty()
                )
            )
            repositoryDB.saveTransaction(transaction)
            repositoryDB.getTransactions().collect {
                if (it.isNotEmpty() && it.size >= SEND_LOT_SIZE) {
                    repository.updateBalance(balance).catch {
                        Log.e("error->", it.message.toString())
                    }.collect { responseBalance ->
                        repository.createTransaction(it).collect { response ->
                            if (response.code == CODE_SUCCESS) {
                                response.data.forEach { transaction ->
                                    repositoryDB.updateTransaction(
                                        transaction.toEntity(
                                            preferencesRepository.getUserKey().getOrNull()
                                                .orEmpty(),
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
        clear()
    }

    fun clear() {
        this.description.value = ""
        this.amount.value = ""
        this.date.value = ""
        this.iconTransaction.value = 0
        validToggleButton()
    }

    fun onChangeSave(description: String, amount: String) {
        this.description.value = description
        this.amount.value = amount
        validToggleButton()
    }

    private fun validToggleButton() {
        toggleButton.value = (isValidDescription() &&
                isValidAmount() &&
                isValidDate()
                && getTypeIcon().value != 0
                )
    }

    private fun isValidDescription(): Boolean {
        return getDescription().value.orEmpty().isNotEmpty()
    }

    private fun isValidAmount(): Boolean {
        return getAmount().value.orEmpty().isNotEmpty()
    }

    private fun isValidDate(): Boolean {
        return onDate().value.orEmpty().isNotEmpty()
    }

    fun onEnableButtonIncome(): LiveData<Boolean> = toggleButton

}