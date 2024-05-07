package com.jvmartinez.finanzapp.core.repository.local.perferences

interface IPreferencesRepository {

    suspend fun getUserName(): Result<String>

    suspend fun setUserName(name: String)

    suspend fun getUserToken(): Result<String>

    suspend fun setUserToken(name: String)

}
