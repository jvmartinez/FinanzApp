package com.jvmartinez.finanzapp.core.di.module

import com.jvmartinez.finanzapp.core.repository.remote.balance.IRepositoryBalance
import com.jvmartinez.finanzapp.core.repository.remote.balance.RepositoryBalance
import com.jvmartinez.finanzapp.core.repository.remote.login.ILoginRepository
import com.jvmartinez.finanzapp.core.repository.remote.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBalanceRepository(
        balance: RepositoryBalance
    ): IRepositoryBalance

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepository: LoginRepository
    ): ILoginRepository
}
