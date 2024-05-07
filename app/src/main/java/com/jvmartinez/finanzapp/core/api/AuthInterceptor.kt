package com.jvmartinez.finanzapp.core.api

import com.jvmartinez.finanzapp.core.repository.local.perferences.IPreferencesRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferencesRepository: IPreferencesRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        runBlocking {
            request.addHeader(
                "Authorization",
                "Bearer ${preferencesRepository.getUserToken().getOrNull().orEmpty()}"
            )
        }
        return chain.proceed(request.build())
    }
}
