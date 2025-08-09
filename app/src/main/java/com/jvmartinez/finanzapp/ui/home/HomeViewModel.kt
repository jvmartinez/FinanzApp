package com.jvmartinez.finanzapp.ui.home

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsapiens.finanzapp.R
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jvmartinez.finanzapp.core.model.Balance
import com.jvmartinez.finanzapp.core.model.Countries
import com.jvmartinez.finanzapp.core.model.Country
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toBalanceView
import com.jvmartinez.finanzapp.core.model.toTransactionViews
import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.IRepositoryBalance
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.utils.Utils.getFirstLastDayOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val balanceRepository: IRepositoryBalance,
    private val repositoryDB: DataBaseRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val loadingData = MutableStateFlow<StatusData<BalanceView>>(StatusData.Empty)

    private val currencyKey = MutableLiveData(Pair("USD", "$"))


    init {
        viewModelScope.launch {
            setCurrencyKey(
                Pair(
                    preferencesRepository.getCurrencyKey().getOrNull() ?: "USD",
                    preferencesRepository.getSymbolKey().getOrNull() ?: "$"
                )
            )
        }
    }

    fun getBalance() {
        viewModelScope.launch {
            loadingData.value = StatusData.Loading
            generateCombineSelectDB().catch {
                processRemote()
            }.collect { (balance, transaction) ->
                balance.balance = ((balance.income ?: 0.0) - (balance.outcome ?: 0.0))
                balance.income = transaction.filter { it.type == 1 && it.amount > 0.0 }.sumOf { it.amount }
                balance.outcome = transaction.filter { it.type == 2  && it.amount > 0.0 }.sumOf { it.amount }
                updateData(balance, transaction)
            }
        }
    }

    private suspend fun processRemote() {
        generateCombineApiResponse().catch {
            loadingData.value = StatusData.Error(it.message.toString())
        }.collect {
            it.first.let { balance ->
                repositoryDB.saveBalance(balance)
                it.second.let { transaction ->
                    if (transaction.isNotEmpty()) {
                        transaction.filter { trans -> trans.amount > 0.0 }.forEach { trans ->
                            repositoryDB.saveTransaction(trans)
                        }
                    }
                    updateData(it.first, it.second)
                }
            }
        }
    }

    private fun updateData(balance: Balance, transaction: List<Transaction>) {
        loadingData.value = (
            StatusData.Success(
                balance.toBalanceView(
                    transaction.toTransactionViews(
                        getCurrencyKey().value?.second.orEmpty()
                    ),
                    getCurrencyKey().value?.second.orEmpty()
                )
            )
        )
    }

    private suspend fun generateCombineApiResponse(): Flow<Pair<Balance, List<Transaction>>> {
        return flow {
            val response = balanceRepository.getBalance()
                .zip(balanceRepository.findByUserTransaction()) { balance, transaction ->
                    Pair(balance.data, transaction.data)
                }
            response.collect {
                emit(it)
            }
        }

    }

    private suspend fun generateCombineSelectDB(): Flow<Pair<Balance, List<Transaction>>> {
        return flow {
            lateinit var balance: Balance
            repositoryDB.getBalance().collect {
                balance = it
            }
            lateinit var transaction: List<Transaction>
            val (firstDayOfMonth, lastDayOfMonth) = getFirstLastDayOfMonth()
            val firstDayFormatted = "${firstDayOfMonth.get(Calendar.DAY_OF_MONTH)}-${firstDayOfMonth.get(Calendar.MONTH) + 1}-${firstDayOfMonth.get(Calendar.YEAR)}"
            val lastDayFormatted = "${lastDayOfMonth.get(Calendar.DAY_OF_MONTH)}-${lastDayOfMonth.get(Calendar.MONTH) + 1}-${lastDayOfMonth.get(Calendar.YEAR)}"
            Log.d("firstDayFormatted", firstDayFormatted)
            Log.d("lastDayFormatted", lastDayFormatted)
            repositoryDB.findAllByDates(firstDayFormatted, lastDayFormatted).collect {
                transaction = it.filter { trans -> trans.amount != 0.0 }
            }
            emit(Pair(balance, transaction))
        }
    }

    fun onLoadingData(): StateFlow<StatusData<BalanceView>> = loadingData
    fun onDismissDialog() {
        loadingData.value = StatusData.Empty
    }

    fun getCountries(context: Context): Countries? {
        val jsonString = context.resources.openRawResource(R.raw.countries)
        val mapper = jacksonObjectMapper()
        val countriesJson = mapper.readValue(jsonString, Countries::class.java)
        return countriesJson
    }

    fun onSelectedCountry(country: Country) {
        viewModelScope.launch {
            preferencesRepository.setCurrencyKey(country.currency)
            preferencesRepository.setSymbolKey(country.symbol)
        }
        setCurrencyKey(Pair(country.currency, country.symbol))
    }

    fun getCurrencyKey(): LiveData<Pair<String, String>> {
        return currencyKey
    }

    private fun setCurrencyKey(key: Pair<String, String>) {
        currencyKey.value = key
    }

    fun onSignOut() {
        viewModelScope.launch {
            preferencesRepository.clearPreferences()
        }
    }
}
