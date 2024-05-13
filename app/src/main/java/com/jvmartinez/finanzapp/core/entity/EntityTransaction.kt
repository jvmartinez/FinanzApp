package com.jvmartinez.finanzapp.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jvmartinez.finanzapp.core.model.Transaction

@Entity(tableName = "transaction")
data class EntityTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("amount") val amount: Double,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("type") val type: Int,
    @ColumnInfo("type_icon") val typeIcon: Int,
    @ColumnInfo("user_key") val userKey: String = "",
    @ColumnInfo("is_synchronized") val isSynchronized: Boolean = false
)


fun EntityTransaction.toModel(): Transaction {
    return Transaction(
        id = this.id.toDouble(),
        amount = this.amount,
        date = this.date,
        description = this.description,
        type = this.type,
        typeIcon = this.typeIcon
    )
}
fun List<EntityTransaction>.toListModel(): List<Transaction> {
    return if (this.isNotEmpty()) {
        this.map {
            it.toModel()
        }
    } else {
        listOf()
    }
}
