package com.jvmartinez.finanzapp.core.repository.local.perferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : IPreferencesRepository {

    override suspend fun getUserName(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->

                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_USERNAME]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setUserName(name: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_USERNAME] = name
            }
        }
    }

    override suspend fun setUserKey(name: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_USER_KEY] = name
            }
        }
    }

    override suspend fun getCurrencyKey(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->

                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_CURRENCY_KEY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setCurrencyKey(name: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_CURRENCY_KEY] = name
            }
        }
    }

    override suspend fun getSymbolKey(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->

                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_SYMBOL_KEY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setSymbolKey(name: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_SYMBOL_KEY] = name
            }
        }
    }

    override suspend fun clearPreferences() {
        setSymbolKey("")
        setUserKey("")
        setUserName("")
        setUserToken("")
        setCurrencyKey("")
    }

    override suspend fun getUserToken(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->

                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_USER_TOKEN]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun setUserToken(name: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_USER_TOKEN] = name
            }
        }
    }

    override suspend fun getUserKey(): Result<String>  {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->

                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[KEY_USER_KEY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    private companion object {

        val KEY_USERNAME = stringPreferencesKey(
            name = "USERNAME"
        )
        val KEY_USER_TOKEN = stringPreferencesKey(
            name = "USER_TOKEN"
        )
        val KEY_USER_KEY = stringPreferencesKey(
            name = "USER_KEY"
        )
        val KEY_CURRENCY_KEY = stringPreferencesKey(
            name = "CURRENCY_KEY"
        )
        val KEY_SYMBOL_KEY = stringPreferencesKey(
            name = "SYMBOL_KEY"
        )
    }
}
