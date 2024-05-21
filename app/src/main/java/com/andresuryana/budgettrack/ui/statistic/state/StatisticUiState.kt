package com.andresuryana.budgettrack.ui.statistic.state

import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal

data class StatisticUiState(
    val isLoading: Boolean = false,
    val monthFilter: String? = null,
    val expenseTotal: ExpenseTotal = ExpenseTotal(0, 0),
    val expenseCategories: List<ExpenseCategory> = emptyList(),
    val summaryDetails: List<ExpenseSummaryDetail> = emptyList(),
)
