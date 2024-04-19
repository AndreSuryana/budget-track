package com.andresuryana.budgettrack.data.dummy

import com.andresuryana.budgettrack.domain.model.expense.Expense
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategory
import com.andresuryana.budgettrack.domain.model.expense.ExpenseCategoryType
import com.andresuryana.budgettrack.domain.model.expense.ExpenseTotal
import com.andresuryana.budgettrack.domain.repository.expense.ExpenseRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.ExpenseError
import java.text.SimpleDateFormat
import java.util.Locale

class DummyExpenseRepository : ExpenseRepository {

    // List of dummy expense categories
    private val categoryHousing = ExpenseCategory(1, "Housing", ExpenseCategoryType.HOUSING)
    private val categoryTransportation = ExpenseCategory(2, "Transportation", ExpenseCategoryType.TRANSPORTATION)
    private val categoryFood = ExpenseCategory(3, "Food", ExpenseCategoryType.FOOD)
    private val categoryHealthcare = ExpenseCategory(4, "Healthcare", ExpenseCategoryType.HEALTHCARE)
    private val categoryPersonal = ExpenseCategory(5, "Personal", ExpenseCategoryType.PERSONAL)
    private val categorySavings = ExpenseCategory(6, "Savings", ExpenseCategoryType.SAVINGS)
    private val categoryTravel = ExpenseCategory(7, "Travel", ExpenseCategoryType.TRAVEL)
    private val categoryInsurance = ExpenseCategory(8, "Insurance", ExpenseCategoryType.INSURANCE)

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

    override suspend fun getExpenseTotal(): Resource<ExpenseTotal, ExpenseError> {
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
                total,
                0 // Make last month difference to 0 for sake of simplicity
            ))
        } catch (e: Exception) {
            Resource.Error(ExpenseError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getRecentExpenses(): Resource<List<Expense>, ExpenseError> = Resource.Success(expenses)
}