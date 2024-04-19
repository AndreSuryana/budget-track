package com.andresuryana.budgettrack.domain.model.expense

data class ExpenseCategory(
    val id: Int?,
    val title: String?,
    val type: ExpenseCategoryType,
)
