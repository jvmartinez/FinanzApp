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
import com.jvmartinez.finanzapp.ui.income.state.IncomeAndOutComeIUState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val iuState = MutableStateFlow(IncomeAndOutComeIUState())
    @RequiresApi(Build.VERSION_CODES.O)
    fun processDate(selectedDateMillis: Long?) {
        selectedDateMillis?.let { dateMillis ->
            val selectedDate =
                Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("UTC")).toLocalDate()
            updateState(
                date = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            )
        } ?: {
            updateState(
                date = ""
            )
        }
        validToggleButton()
    }

    fun updateState(
        date: String? = null,
        description: String? = null,
        amount: String? = null,
        toggleButton: Boolean? = null,
        iconTransaction: Int? = null,
        listCategory: List<CategoryModel>? = null,
        listOutComeCategory: List<CategoryModel>? = null,
    ) {
        iuState.value = iuState.value.copy(
            date = date ?: iuState.value.date,
            description = description ?: iuState.value.description,
            amount = amount ?: iuState
                .value.amount,
            toggleButton = toggleButton ?: iuState.value.toggleButton,
            listCategory = listCategory ?: iuState.value.listCategory,
            listOutComeCategory = listOutComeCategory ?: iuState.value.listOutComeCategory,
            iconTransaction = iconTransaction ?: iuState.value.iconTransaction,
        )
    }

    fun setTpeTransaction(category: CategoryModel, type: Int) {
        if (type == 1) {
            val updateAll = iuState.value.listCategory.map {
                if (it.id == category.id) {
                    it.copy(selected = !it.selected)
                } else {
                    it.copy(selected = false)
                }
            }
            updateState(
                listCategory = updateAll
            )
        } else {
            val updateAll = iuState.value.listOutComeCategory.map {
                if (it.id == category.id) {
                    it.copy(selected = !it.selected)
                } else {
                    it.copy(selected = false)
                }
            }
            updateState(
                listOutComeCategory = updateAll
            )
        }
        updateState(
            iconTransaction = category.iconDrawable
        )
        validToggleButton()
    }

    fun save(type: Int) {
        val transaction = Transaction(
            id = 0.0,
            amount = iuState.value.amount.toDouble(),
            date = iuState.value.date,
            description = iuState.value.description,
            type = type,
            typeIcon = iuState.value.iconTransaction,
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
        iuState.value = IncomeAndOutComeIUState()
        validToggleButton()
    }

    fun onChangeSave(description: String, amount: String) {
        updateState(
            description = description,
            amount = amount
        )
        validToggleButton()
    }

    private fun validToggleButton() {
        updateState(
            toggleButton = (isValidDescription() &&
                    isValidAmount() &&
                    isValidDate()
                    && iuState.value.iconTransaction != 0
                    )

        )
    }

    private fun isValidDescription(): Boolean {
        return iuState.value.description.isNotEmpty()
    }

    private fun isValidAmount(): Boolean {
        return iuState.value.amount.isNotEmpty()
    }

    private fun isValidDate(): Boolean {
        return iuState.value.date.isNotEmpty()
    }

    fun onIUState(): StateFlow<IncomeAndOutComeIUState> = iuState
}