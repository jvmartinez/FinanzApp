package com.jvmartinez.finanzapp.core.di

import android.content.Context
import androidx.room.Room
import com.jvmartinez.finanzapp.core.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModel {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-finanz-app"
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration(false)
        .addMigrations()
        .build()

    @Singleton
    @Provides
    fun providerBalanceDao(db: AppDatabase) = db.balanceDao()

    @Singleton
    @Provides
    fun providerTransactionDao(db: AppDatabase) = db.transactionDao()

}
