package com.jvmartinez.finanzapp.utils

import android.content.Context
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.core.model.CategoryModel

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
}