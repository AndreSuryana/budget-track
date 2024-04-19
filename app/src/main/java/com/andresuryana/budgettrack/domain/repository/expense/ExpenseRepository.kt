package com.andresuryana.budgettrack.domain.repository.expense

import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError

interface ExpenseRepository {

    suspend fun getExpenseTotal(): Resource<ExpenseTotal, ExpenseError>

    suspend fun getRecentExpenses(): Resource<List<Expense>, ExpenseError>
}