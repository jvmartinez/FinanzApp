package com.jvmartinez.finanzapp.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jvmartinez.finanzapp.core.entity.EntityBalance

@Dao
interface BalanceDao {

    @Query("SELECT * FROM balance")
    suspend fun findAll(): List<EntityBalance>

    @Query("SELECT * FROM balance WHERE id in (:id)")
    suspend fun findById(id: String): EntityBalance

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balance: EntityBalance)


    @Update
    suspend fun update(balance: EntityBalance)

}
