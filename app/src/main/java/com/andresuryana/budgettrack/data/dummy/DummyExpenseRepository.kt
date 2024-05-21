package com.andresuryana.budgettrack.data.dummy

import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategoryType
import com.andresuryana.budgettrack.domain.model.expense.ExpenseSummaryDetail
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.repository.expense.ExpenseRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.ExpenseCategoryError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError
import com.andresuryana.budgettrack.domain.resource.error.ExpenseSummaryError
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

class DummyExpenseRepository : ExpenseRepository {

    // List of dummy expense categories
    private val categoryHousing = ExpenseCategory(1, "Housing", 2_000_000, ExpenseCategoryType.HOUSING)
    private val categoryTransportation = ExpenseCategory(2, "Transportation", 500_000, ExpenseCategoryType.TRANSPORTATION)
    private val categoryFood = ExpenseCategory(3, "Food", 1_500_000, ExpenseCategoryType.FOOD)
    private val categoryHealthcare = ExpenseCategory(4, "Healthcare", 500_000, ExpenseCategoryType.HEALTHCARE)
    private val categoryPersonal = ExpenseCategory(5, "Personal", 750_000, ExpenseCategoryType.PERSONAL)
    private val categorySavings = ExpenseCategory(6, "Savings", 2_000_000, ExpenseCategoryType.SAVINGS)
    private val categoryTravel = ExpenseCategory(7, "Travel", 1_00_000, ExpenseCategoryType.TRAVEL)
    private val categoryInsurance = ExpenseCategory(8, "Insurance", 1_250_000, ExpenseCategoryType.INSURANCE)

    private val categories = listOf(
        categoryHousing,
        categoryTransportation,
        categoryFood,
        categoryHealthcare,
        categoryPersonal,
        categorySavings,
        categoryTransportation,
        categoryInsurance
    )

    // Date formatter
    private val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())

    // List of dummy expenses
    private val expenses = arrayListOf(
        Expense(1, "Rent Payment", "Monthly rent", 1000000, sdf.parse("2024-02-28"), categoryHousing),
        Expense(2, "Gasoline", "Car fuel", 50000, sdf.parse("2024-02-28"), categoryTransportation),
        Expense(3, "Lunch", "Workday meal", 15000, sdf.parse("2024-02-27"), categoryFood),
        Expense(4, "Doctor's Visit", "Health checkup", 200000, sdf.parse("2024-02-26"), categoryHealthcare),
        Expense(5, "Haircut", "Personal grooming", 30000, sdf.parse("2024-02-25"), categoryPersonal),
        Expense(6, "Mortgage Payment", "Monthly mortgage", 1500, sdf.parse("2024-02-24"), categoryHousing),
        Expense(7, "Bus Fare", "Public transport", 10000, sdf.parse("2024-02-23"), categoryTransportation),
        Expense(8, "Dinner", "Evening meal", 20000, sdf.parse("2024-02-22"), categoryFood),
        Expense(9, "Dentist Appointment", "Routine checkup", 100, sdf.parse("2024-02-21"), categoryHealthcare),
        Expense(10, "Clothing", "Shopping", 50000, sdf.parse("2024-02-20"), categoryPersonal),
        Expense(11, "Utility Bills", "Electricity, water, etc.", 200, sdf.parse("2024-02-19"), categoryHousing),
        Expense(12, "Taxi Fare", "Ride-sharing", 15000, sdf.parse("2024-02-18"), categoryTransportation),
        Expense(13, "Breakfast", "Morning meal", 10000, sdf.parse("2024-02-17"), categoryFood),
        Expense(14, "Medication", "Prescriptions", 50000, sdf.parse("2024-02-16"), categoryHealthcare),
        Expense(15, "Books", "Reading materials", 30000, sdf.parse("2024-02-15"), categoryPersonal),
        Expense(16, "Savings Deposit", "Monthly savings", 500000, sdf.parse("2024-02-14"), categorySavings),
        Expense(17, "Flight Tickets", "Travel expenses", 1000000, sdf.parse("2024-02-13"), categoryTravel),
        Expense(18, "Health Insurance", "Monthly premium", 150000, sdf.parse("2024-02-12"), categoryInsurance),
        Expense(19, "Car Insurance", "Annual insurance", 400000, sdf.parse("2024-02-11"), categoryInsurance),
        Expense(20, "Hotel Booking", "Travel accommodation", 300000, sdf.parse("2024-02-10"), categoryTravel) //
    )

    override suspend fun getExpenseTotal(startDate: Instant?, endDate: Instant?): Resource<ExpenseTotal, ExpenseError> {
        return try {
            // Check if expenses empty
            if (expenses.isEmpty()) {
                return Resource.Error(ExpenseError.EMPTY_EXPENSES)
            }

            // Calculate current expense total
            var total = 0L
            expenses.forEach {
                total += it.amount ?: 0
            }

            Resource.Success(ExpenseTotal(
                total = total,
                diffLastMonth = -250_000,
                limit = 10_000_000
            ))
        } catch (e: Exception) {
            Resource.Error(ExpenseError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getRecentExpenses(startDate: Instant?, endDate: Instant?): Resource<List<Expense>, ExpenseError> = Resource.Success(expenses)

    override suspend fun getExpenseCategories(startDate: Instant?, endDate: Instant?): Resource<List<ExpenseCategory>, ExpenseCategoryError> {
        // Create a copy of categories to avoid modifying the original objects
        val expenseCategories = ArrayList(categories.map { it.copy() })

        // Iterate through expenses and update spent amount for the corresponding categories
        expenses.forEach { expense ->
            val category = expense.category
            val matchingCategory = expenseCategories.find { it.id == category.id }
            matchingCategory?.let { it.spent = it.spent?.plus(expense.amount ?: 0) }
        }


        return Resource.Success(expenseCategories)
    }

    override suspend fun getExpenseSummaries(startDate: Instant?, endDate: Instant?): Resource<List<ExpenseSummaryDetail>, ExpenseSummaryError> =
        Resource.Success(
            listOf(
                ExpenseSummaryDetail(1, "Monthly Income", 1000000),
                ExpenseSummaryDetail(2, "Monthly Expenses", -250000),
                ExpenseSummaryDetail(3, "Savings", 500000)
            )
        )
}