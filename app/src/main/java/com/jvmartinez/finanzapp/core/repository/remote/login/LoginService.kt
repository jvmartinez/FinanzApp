package com.jvmartinez.finanzapp.core.repository.remote.login

import com.jvmartinez.finanzapp.core.api.ResponseBasic
import com.jvmartinez.finanzapp.core.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/balance/signin")
    suspend fun signIn(@Body loginRequest: UserModel): ResponseBasic<UserModel>

    @POST("/balance/signup")
    suspend fun signUp(@Body userModel: UserModel): ResponseBasic<UserModel>

    @POST("/balance/reset/password")
    suspend fun resetPassword(@Body resetPasswordRequest: Map<String, String>): ResponseBasic<Boolean>

    @POST("/balance/logout")
    suspend fun logout(): ResponseBasic<Boolean>
}
