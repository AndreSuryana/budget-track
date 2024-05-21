package com.andresuryana.budgettrack.ui.statistic

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresuryana.budgettrack.R
import com.andresuryana.budgettrack.domain.event.SingleEvent
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.repository.expense.ExpenseRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.ExpenseCategoryError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseSummaryError
import com.andresuryana.budgettrack.ui.statistic.state.StatisticUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    var uiState = MutableStateFlow(StatisticUiState())
        private set

    private val _toastEvent = MutableLiveData<SingleEvent<Int>>()
    val toastEvent: LiveData<SingleEvent<Int>> = _toastEvent

    private var lastToastResId: Int? = null

    var filterDate: Calendar
        private set

    // Date range for the filter
    private var startDate: Instant? = null
    private var endDate: Instant? = null

    init {
        // Set initial value for filterDate to current date
        filterDate = Calendar.getInstance()
        onDateFilterChanged(filterDate.get(Calendar.MONTH), filterDate.get(Calendar.YEAR))

        // Load the UI data for the first time
        loadUiData()
    }

    fun loadUiData() {
        viewModelScope.launch {
            // Reset the lastToastResId
            lastToastResId = null

            toggleLoading()
            delay(1_000) // FIXME: Just for development only!
            getExpenseTotal()
            getCategoryDetail()
            getOverallDetails()
            toggleLoading()
        }
    }

    fun onDateFilterChanged(monthOfYear: Int, year: Int) {
        // Update the date filter for the start date (beginning of the month)
        val dateFilterStart = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Update the date filter for the end date (end of the month)
        val dateFilterEnd = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        startDate = dateFilterStart.time.toInstant()
        endDate = dateFilterEnd.time.toInstant()

        // Update the filterDate
        this.filterDate = dateFilterStart

        // Reload the UI data with the new date filter
        loadUiData()
    }

    private suspend fun getExpenseTotal() {
        when (val result = expenseRepository.getExpenseTotal(startDate, endDate)) {
            is Resource.Success -> uiState.update { it.copy(expenseTotal = result.data) }
            is Resource.Error -> {
                when (result.error) {
                    ExpenseError.EMPTY_EXPENSES -> uiState.update { it.copy(expenseTotal = ExpenseTotal()) }
                    ExpenseError.UNKNOWN_ERROR -> showToast(R.string.error_unknown)
                }
            }
        }
    }

    private suspend fun getCategoryDetail() {
        when (val result = expenseRepository.getExpenseCategories(startDate, endDate)) {
            is Resource.Success -> uiState.update { it.copy(expenseCategories = result.data) }
            is Resource.Error -> {
                uiState.update { it.copy(expenseCategories = emptyList()) }
                if (result.error == ExpenseCategoryError.UNKNOWN_ERROR) {
                    showToast(R.string.error_unknown)
                }
            }
        }
    }

    private suspend fun getOverallDetails() {
        when (val result = expenseRepository.getExpenseSummaries(startDate, endDate)) {
            is Resource.Success -> uiState.update { it.copy(summaryDetails = result.data) }
            is Resource.Error -> {
                uiState.update { it.copy(expenseCategories = emptyList()) }
                if (result.error == ExpenseSummaryError.UNKNOWN_ERROR) {
                    showToast(R.string.error_unknown)
                }
            }
        }
    }

    private fun toggleLoading() {
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = !it.isLoading) }
        }
    }

    private fun showToast(@StringRes resId: Int) {
        if (resId != lastToastResId) {
            _toastEvent.value = SingleEvent(resId)
        }
    }
}