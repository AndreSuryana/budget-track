package com.andresuryana.budgettrack.domain.model.account

import com.andresuryana.budgettrack.domain.model.BaseModel

data class Account(
    override val id: Int?,
    val title: String?,
    val amount: Long?,
    val type: AccountType,
) : BaseModel(id)
