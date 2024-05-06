package com.jvmartinez.finanzapp.core.repository.remote.login

import com.jvmartinez.finanzapp.core.api.ResponseBasic
import com.jvmartinez.finanzapp.core.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun signIn(email: String, password: String): Flow<ResponseBasic<UserModel>>
    suspend fun signUp(userModel: UserModel): Flow<ResponseBasic<UserModel>>
    suspend fun resetPassword(email: String): Flow<ResponseBasic<Boolean>>
    suspend fun signOut(): Flow<ResponseBasic<Boolean>>
}
