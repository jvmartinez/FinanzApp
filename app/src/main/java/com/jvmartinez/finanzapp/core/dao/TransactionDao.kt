package com.jvmartinez.finanzapp.core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jvmartinez.finanzapp.core.entity.EntityTransaction


@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` where user_key = :userKey order by date desc")
    suspend fun findAll(userKey: String): List<EntityTransaction>

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    suspend fun findById(id: Int): EntityTransaction

    @Query("SELECT sum(amount) FROM `transaction` WHERE user_key = :userKey and type= :type")
    suspend fun sumIncomeOrOutcomeTotal(userKey: String, type: Int): Double

    @Delete
    suspend fun deleteById(transaction: EntityTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: EntityTransaction)

    @Update
    suspend fun update(transaction: EntityTransaction)

    @Query("SELECT * FROM `transaction` where user_key = :userKey and date between :dateStart and :dateEnd")
    suspend fun findAllByDate(
        userKey: String,
        dateStart: String,
        dateEnd: String
    ): List<EntityTransaction>

}