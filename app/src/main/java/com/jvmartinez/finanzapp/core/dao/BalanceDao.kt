package com.jvmartinez.finanzapp.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jvmartinez.finanzapp.core.entity.EntityBalance

@Dao
interface BalanceDao {

    @Query("SELECT * FROM balance")
    suspend fun findAll(): List<EntityBalance>

    @Query("SELECT * FROM balance WHERE id in (:id)")
    suspend fun findById(id: String): EntityBalance

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balance: EntityBalance)


    @Query("UPDATE balance SET " +
            "balance = :balance and " +
            "income = :income and" +
            " outcome = :outcome and " +
            "create_at = :createAt and " +
            "update_at = :updateAt WHERE id = :id")
    suspend fun update(
        id: String,
        balance: Double,
        income: Double,
        outcome: Double,
        createAt: String,
        updateAt: String
    )

}
