package com.andresuryana.budgettrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.repository.account.AccountRepository
import com.andresuryana.budgettrack.domain.repository.expense.ExpenseRepository
import com.andresuryana.budgettrack.domain.repository.user.UserRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.AccountError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError
import com.andresuryana.budgettrack.domain.resource.error.UserError
import com.andresuryana.budgettrack.ui.home.state.HomeUiState
import com.andresuryana.budgettrack.ui.home.state.HomeUiState.GreetingDetail
import com.andresuryana.budgettrack.ui.home.state.HomeUiState.GreetingText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    var uiState = MutableStateFlow(HomeUiState())
        private set

    init {
        loadUiData()
    }

    fun loadUiData() {
        viewModelScope.launch {
            toggleLoading()
            delay(3_000) // FIXME: Just for development only!
            getGreetingState()
            getExpenseTotalState()
            getUserAccounts()
            getRecentExpenseState()
            toggleLoading()
        }
    }

    fun toggleExpenseAmountVisibility(isChecked: Boolean) {
        viewModelScope.launch {
            uiState.update { it.copy(expenseTotalVisibility = isChecked) }
        }
    }

    private fun getGreetingState() {
        viewModelScope.launch {
            val greetingText = getGreetingText()
            when (val result = userRepository.getActiveUser()) {
                is Resource.Success -> uiState.update { state ->
                    state.copy(
                        greetingText = greetingText,
                        greetingDetail = GreetingDetail.Authenticated(result.data)
                    )
                }

                is Resource.Error -> {
                    when (result.error) {
                        UserError.USER_NOT_FOUND -> uiState.update {
                            it.copy(
                                greetingText = greetingText,
                                greetingDetail = GreetingDetail.Guest
                            )
                        }

                        UserError.UNKNOWN_ERROR -> TODO("Show as one-time event for the error message")
                    }
                }
            }
        }
    }

    private fun getExpenseTotalState() {
        viewModelScope.launch {
            when (val result = expenseRepository.getExpenseTotal()) {
                is Resource.Success -> uiState.update { it.copy(expenseTotal = result.data) }
                is Resource.Error -> {
                    when (result.error) {
                        ExpenseError.EMPTY_EXPENSES -> uiState.update { it.copy(expenseTotal = ExpenseTotal(0, 0)) }
                        ExpenseError.UNKNOWN_ERROR -> TODO("Show as one-time event for the error message")
                    }
                }
            }
        }
    }

    private fun getUserAccounts() {
        viewModelScope.launch {
            when (val result = accountRepository.getAccounts()) {
                is Resource.Success -> uiState.update { it.copy(accounts = result.data) }
                is Resource.Error -> {
                    when (result.error) {
                        AccountError.EMPTY_ACCOUNTS -> uiState.update { it.copy(accounts = emptyList()) }
                        AccountError.UNKNOWN_ERROR -> TODO("Show as one-time event for the error message")
                    }
                }
            }
        }
    }

    private fun getRecentExpenseState() {
        viewModelScope.launch {
            when (val result = expenseRepository.getRecentExpenses()) {
                is Resource.Success -> uiState.update { it.copy(recentExpenses = result.data) }
                is Resource.Error -> {
                    when (result.error) {
                        ExpenseError.EMPTY_EXPENSES -> uiState.update { it.copy(recentExpenses = emptyList()) }
                        ExpenseError.UNKNOWN_ERROR -> TODO("Show as one-time event for the error message")
                    }
                }
            }
        }
    }

    private fun toggleLoading() {
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = !it.isLoading) }
        }
    }

    private fun getGreetingText(): GreetingText {
        val currentHour = LocalTime.now().hour
        return when (currentHour) {
            in 0..11 -> GreetingText.MORNING
            in 12..15 -> GreetingText.AFTERNOON
            in 16..20 -> GreetingText.EVENING
            in 21..23 -> GreetingText.NIGHT
            else -> GreetingText.DEFAULT
        }
    }
}