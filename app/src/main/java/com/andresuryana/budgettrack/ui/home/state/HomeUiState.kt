package com.andresuryana.budgettrack.ui.home.state

import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.model.user.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val greetingText: GreetingText = GreetingText.DEFAULT,
    val greetingDetail: GreetingDetail = GreetingDetail.Guest,
    val expenseTotal: ExpenseTotal = ExpenseTotal(0, 0),
    val expenseTotalVisibility: Boolean = false,
    val accounts: List<Account> = listOf(),
    val recentExpenses: List<Expense> = listOf(),
) {
    sealed interface GreetingDetail {
        data class Authenticated(val user: User) : GreetingDetail
        data object Guest : GreetingDetail
    }

    enum class GreetingText {
        MORNING, AFTERNOON, EVENING, NIGHT, DEFAULT
    }
}
