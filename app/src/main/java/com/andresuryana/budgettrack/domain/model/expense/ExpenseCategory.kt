package com.andresuryana.budgettrack.domain.model.expense

import com.andresuryana.budgettrack.domain.model.BaseModel

data class ExpenseCategory(
    override val id: Int?,
    val title: String?,
    val limit: Long?,
    val type: ExpenseCategoryType,
    var spent: Long? = 0,
) : BaseModel(id) {

    fun getProgressPercentage(): Int {
        // Use limit directly if not null, otherwise default to 1
        val denominator = limit ?: 1

        // Avoid division by zero
        if (denominator == 0L) return 0

        val progress = spent ?: 0
        return (progress * 100 / denominator).toInt()
    }
}
