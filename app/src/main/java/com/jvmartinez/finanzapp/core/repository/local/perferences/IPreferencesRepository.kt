package com.jvmartinez.finanzapp.core.repository.local.perferences

interface IPreferencesRepository {

    suspend fun getUserName(): Result<String>

    suspend fun setUserName(name: String)

    suspend fun getUserToken(): Result<String>

    suspend fun setUserToken(name: String)

    suspend fun getUserKey(): Result<String>

    suspend fun setUserKey(name: String)

    suspend fun getCurrencyKey(): Result<String>

    suspend fun setCurrencyKey(name: String)

    suspend fun getSymbolKey(): Result<String>

    suspend fun setSymbolKey(name: String)
}
