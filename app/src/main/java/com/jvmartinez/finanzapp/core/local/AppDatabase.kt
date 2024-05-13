package com.jvmartinez.finanzapp.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jvmartinez.finanzapp.core.dao.BalanceDao
import com.jvmartinez.finanzapp.core.dao.TransactionDao
import com.jvmartinez.finanzapp.core.entity.EntityBalance
import com.jvmartinez.finanzapp.core.entity.EntityTransaction

@Database(
    entities = [EntityBalance::class, EntityTransaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun balanceDao(): BalanceDao

    abstract fun transactionDao(): TransactionDao
}
