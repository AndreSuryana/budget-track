package com.andresuryana.budgettrack.domain.model.expense

import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.andresuryana.budgettrack.R

enum class ExpenseCategoryType(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    private val iconColor: String,
) {
    HOUSING(R.string.category_housing, R.drawable.category_housing, "#2196F3"),
    TRANSPORTATION(R.string.category_transportation, R.drawable.category_transportation, "#4CAF50"),
    FOOD(R.string.category_food, R.drawable.category_food, "#FFC107"),
    HEALTHCARE(R.string.category_healthcare, R.drawable.category_healthcare, "#E91E63"),
    PERSONAL(R.string.category_personal, R.drawable.category_personal, "#9C27B0"),
    DEBT(R.string.category_debt, R.drawable.category_debt, "#FF5722"),
    ENTERTAINMENT(R.string.category_entertainment, R.drawable.category_entertainment, "#FF9800"),
    EDUCATION(R.string.category_education, R.drawable.category_education, "#795548"),
    SAVINGS(R.string.category_savings, R.drawable.category_savings, "#03A9F4"),
    TRAVEL(R.string.category_travel, R.drawable.category_travel, "#FFEB3B"),
    INSURANCE(R.string.category_insurance, R.drawable.category_insurance, "#607D8B"),
    INVESTMENTS(R.string.category_investments, R.drawable.category_investments, "#8BC34A"),
    OTHERS(R.string.category_others, R.drawable.category_others, "#9E9E9E");

    fun getIconColor(): Int {
        return try {
            Color.parseColor(iconColor)
        } catch (e: Exception) {
            Color.BLACK
        }
    }
}