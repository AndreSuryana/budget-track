package com.andresuryana.budgettrack.domain.repository.account

import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.AccountError

interface AccountRepository {

    suspend fun getAccounts(): Resource<List<Account>, AccountError>
}