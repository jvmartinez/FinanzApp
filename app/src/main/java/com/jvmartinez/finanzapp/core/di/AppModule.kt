package com.jvmartinez.finanzapp.core.di

import com.jvmartinez.finanzapp.core.repository.local.bd.DataBaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerRepository(
        repositoryDB: DataBaseRepository
    ) = repositoryDB
}