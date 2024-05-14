package com.jvmartinez.finanzapp.core.extesions


import java.text.SimpleDateFormat
import java.util.Date

fun Date.dateString(): String = SimpleDateFormat("yyyy-MM-dd").format(this)