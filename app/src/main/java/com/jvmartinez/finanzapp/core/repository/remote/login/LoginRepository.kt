package com.jvmartinez.finanzapp.core.repository.remote.login

import com.jvmartinez.finanzapp.core.api.ResponseBasic
import com.jvmartinez.finanzapp.core.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginService: LoginService
) : ILoginRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<ResponseBasic<UserModel>> = flow {
        emit(loginService.signIn(UserModel(email = email, password = password)))
    }

    override suspend fun signUp(userModel: UserModel): Flow<ResponseBasic<UserModel>> = flow {
        emit(loginService.signUp(userModel))
    }

    override suspend fun resetPassword(email: String): Flow<ResponseBasic<Boolean>> = flow {
        emit(loginService.resetPassword(mapOf("email" to email)))
    }


    override suspend fun signOut(): Flow<ResponseBasic<Boolean>> = flow {
        emit(loginService.logout())
    }

}
