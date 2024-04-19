package com.andresuryana.budgettrack.domain.model.expense

import com.andresuryana.budgettrack.domain.model.BaseModel
import java.util.Date

data class Expense(
    override val id: Int?,
    val title: String?,
    val description: String?,
    val amount: Long?,
    val timestamp: Date?,
    val category: ExpenseCategory,
) : BaseModel(id)
