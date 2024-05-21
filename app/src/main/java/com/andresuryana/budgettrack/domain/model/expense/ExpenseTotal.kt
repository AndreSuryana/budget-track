package com.andresuryana.budgettrack.domain.model.expense

data class ExpenseTotal(
    val total: Long? = 0,
    val diffLastMonth: Long? = 0,
    val limit: Long? = 0,
) {

    fun getProgressPercentage(): Int {
        // Use limit directly if not null, otherwise default to 1
        val denominator = limit ?: 1

        // Avoid division by zero
        if (denominator == 0L) return 0

        val progress = total ?: 0
        return (progress * 100 / denominator).toInt()
    }
}
