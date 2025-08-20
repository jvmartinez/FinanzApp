package com.jvmartinez.finanzapp.core.di

import com.devsapiens.finanzapp.BuildConfig
import com.jvmartinez.finanzapp.core.api.AuthInterceptor
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import com.jvmartinez.finanzapp.core.repository.remote.balance.BalanceService
import com.jvmartinez.finanzapp.core.repository.remote.login.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Suppress("FunctionOnlyReturningConstant")
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideAuthInterceptor(preferencesRepository: PreferencesRepository): AuthInterceptor {
        return AuthInterceptor(preferencesRepository)
    }

    @Provides
    @Singleton
    fun apiServiceBalance(retrofit: Retrofit): BalanceService = retrofit.create(
        BalanceService::class.java
    )

    @Provides
    @Singleton
    fun apiServiceLogin(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

}
