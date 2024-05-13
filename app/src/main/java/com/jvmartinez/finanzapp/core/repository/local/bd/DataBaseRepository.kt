package com.jvmartinez.finanzapp.core.repository.local.bd

import com.jvmartinez.finanzapp.core.dao.BalanceDao
import com.jvmartinez.finanzapp.core.dao.TransactionDao
import com.jvmartinez.finanzapp.core.entity.EntityTransaction
import com.jvmartinez.finanzapp.core.entity.toBalance
import com.jvmartinez.finanzapp.core.entity.toListModel
import com.jvmartinez.finanzapp.core.model.Balance
import com.jvmartinez.finanzapp.core.model.Transaction
import com.jvmartinez.finanzapp.core.model.toEntity
import com.jvmartinez.finanzapp.core.repository.local.perferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val balanceDao: BalanceDao,
    private val preferences: PreferencesRepository
) {
    suspend fun getTransactions(): Flow<List<Transaction>> = flow {
        emit(
            transactionDao.findAll(
                preferences.getUserKey().getOrNull().orEmpty()
            ).toListModel()
        )
    }

    suspend fun getBalance(): Flow<Balance> = flow {
        emit(
            balanceDao.findById(
                preferences.getUserKey().getOrNull().orEmpty()
            ).toBalance()
        )
    }

    suspend fun saveTransaction(transaction: Transaction) =
        transactionDao.insert(transaction.toEntity(preferences.getUserKey().getOrNull().orEmpty()))

    suspend fun saveBalance(balance: Balance) = balanceDao.insert(
        balance.toEntity(
            preferences.getUserKey().getOrNull().orEmpty()
        )
    )

    suspend fun deleteTransaction(
        transaction: Transaction,
    ) = transactionDao.deleteById(transaction.toEntity(preferences.getUserKey().getOrNull().orEmpty()))

    suspend fun updateTransaction(transaction: EntityTransaction) =
        transactionDao.update(transaction)
}