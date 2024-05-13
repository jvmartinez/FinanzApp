package com.jvmartinez.finanzapp.core.model

data class CategoryModel(
    val id: Int,
    val name: String,
    val iconDrawable: Int,
    val iconURL: String? = null,
    var visible: Boolean = false,
    var selected: Boolean = false
)
