package com.andresuryana.budgettrack.domain.repository.expense

import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.ExpenseCategoryError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseSummaryError
import java.time.Instant

interface ExpenseRepository {

    suspend fun getExpenseTotal(startDate: Instant? = null, endDate: Instant? = null): Resource<ExpenseTotal, ExpenseError>

    suspend fun getRecentExpenses(startDate: Instant? = null, endDate: Instant? = null): Resource<List<Expense>, ExpenseError>

    suspend fun getExpenseCategories(startDate: Instant? = null, endDate: Instant? = null): Resource<List<ExpenseCategory>, ExpenseCategoryError>

    suspend fun getExpenseSummaries(startDate: Instant? = null, endDate: Instant? = null): Resource<List<ExpenseSummaryDetail>, ExpenseSummaryError>
}