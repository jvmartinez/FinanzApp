package com.jvmartinez.finanzapp.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jvmartinez.finanzapp.core.model.Balance

@Entity(tableName = "balance")
data class EntityBalance(

    @PrimaryKey
    val id: String = "",
    @ColumnInfo(name = "balance") val balance: Double = 0.0,
    @ColumnInfo("income") val income: Double = 0.0,
    @ColumnInfo("outcome") val outcome: Double = 0.0,
    @ColumnInfo("create_at") val createAt: String = "",
    @ColumnInfo("update_at") val updateAt: String = "",
    @ColumnInfo("is_synchronized") val isSynchronized: Boolean = false
)

fun EntityBalance.toBalance(): Balance {
    return Balance(
        id = id,
        balance = balance,
        income = income,
        outcome = outcome,
        createAt = createAt,
        updateAt = updateAt)
}