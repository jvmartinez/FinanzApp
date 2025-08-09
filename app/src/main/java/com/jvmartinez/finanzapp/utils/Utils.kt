package com.jvmartinez.finanzapp.utils

import android.content.Context
import com.devsapiens.finanzapp.R
import com.jvmartinez.finanzapp.core.model.CategoryModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {
    fun getListCategoryIncome(context: Context): MutableList<CategoryModel> {
        val categories: MutableList<CategoryModel> = mutableListOf()
        categories.add(
            CategoryModel(
                0,
                context.getString(R.string.label_saving),
                R.drawable.ic_saving
            )
        )
        categories.add(
            CategoryModel(
                1,
                context.getString(R.string.label_salary),
                R.drawable.ic_twotone_monetization_on_24
            )
        )
        categories.add(
            CategoryModel(
                2,
                context.getString(R.string.label_extra),
                R.drawable.ic_twotone_attach_money_24
            )
        )
        categories.add(
            CategoryModel(
                3,
                context.getString(R.string.label_accounts_collected),
                R.drawable.ic_twotone_currency_exchange_24
            )
        )
        categories.add(
            CategoryModel(
                4,
                context.getString(R.string.label_others),
                R.drawable.ic_twotone_ballot_24,
                visible = true
            )
        )
        return categories
    }

    fun getListCategoryExpenses(context: Context): MutableList<CategoryModel> {
        val categories: MutableList<CategoryModel> = mutableListOf()
        categories.add(
            CategoryModel(
                0,
                context.getString(R.string.label_food),
                R.drawable.ic_twotone_restaurant_24
            )
        )
        categories.add(
            CategoryModel(
                1,
                context.getString(R.string.label_gift),
                R.drawable.ic_twotone_card_giftcard_24
            )
        )
        categories.add(
            CategoryModel(
                2,
                context.getString(R.string.label_health),
                R.drawable.ic_twotone_health_and_safety_24
            )
        )
        categories.add(
            CategoryModel(
                3,
                context.getString(R.string.label_living_place),
                R.drawable.ic_twotone_maps_home_work_24
            )
        )
        categories.add(
            CategoryModel(
                4,
                context.getString(R.string.label_transport),
                R.drawable.ic_twotone_tram_24
            )
        )
        categories.add(
            CategoryModel(
                5,
                context.getString(R.string.label_personal_expenses),
                R.drawable.ic_twotone_emoji_people_24
            )
        )
        categories.add(
            CategoryModel(
                6,
                context.getString(R.string.label_pets),
                R.drawable.ic_baseline_pets_24
            )
        )
        categories.add(
            CategoryModel(
                7,
                context.getString(R.string.label_supplies),
                R.drawable.ic_twotone_maps_home_work_24
            )
        )
        categories.add(
            CategoryModel(
                8,
                context.getString(R.string.label_trips),
                R.drawable.ic_twotone_emoji_transportation_24
            )
        )
        categories.add(
            CategoryModel(
                9,
                context.getString(R.string.label_debt),
                R.drawable.ic_twotone_sentiment_very_dissatisfied_24
            )
        )
        categories.add(
            CategoryModel(
                10,
                context.getString(R.string.label_others),
                R.drawable.ic_twotone_ballot_24,
                visible = true
            )
        )
        return categories
    }

    fun formatAmount(amount: Double, symbol: String): String {
        return "$symbol ${String.format(Locale.getDefault(), "%.2f", amount)}"
    }

    fun getFormattedDate(timeInMillis: Long): String{
        val calender = Calendar.getInstance()
        calender.timeInMillis = timeInMillis
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        return dateFormat.format(calender.timeInMillis)
    }

    fun getFirstLastDayOfMonth(): Pair<Calendar, Calendar> {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.clone() as Calendar

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DATE, -1)
        val lastDayOfMonth = calendar

        return Pair(firstDayOfMonth, lastDayOfMonth)
    }
}