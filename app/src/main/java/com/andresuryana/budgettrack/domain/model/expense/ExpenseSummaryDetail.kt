package com.andresuryana.budgettrack.domain.model.expense

import com.andresuryana.budgettrack.domain.model.BaseModel

data class ExpenseSummaryDetail(
    override val id: Int?,
    val title: String?,
    val amount: Long?,
) : BaseModel(id) {

    enum class SummaryType
}
