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

    @Query("SELECT * FROM `transaction` where user_key = :userKey and is_synchronized = 0")
    suspend fun findAll(userKey: String): List<EntityTransaction>

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    suspend fun findById(id: Int): EntityTransaction

    @Delete
    suspend fun deleteById(transaction: EntityTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: EntityTransaction)

    @Update
    suspend fun update(transaction: EntityTransaction)

}