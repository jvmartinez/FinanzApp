package com.jvmartinez.finanzapp.core.extesions


import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

@TypeConverter
fun String.toDateSql(): Date {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.parse(this)
    val sqlDate = Date(date?.time ?: 0)
     return sqlDate
}