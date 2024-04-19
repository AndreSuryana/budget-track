package com.andresuryana.budgettrack.data.dummy

import com.andresuryana.budgettrack.domain.model.account.Account
import com.andresuryana.budgettrack.domain.model.account.AccountType
import com.andresuryana.budgettrack.domain.repository.account.AccountRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.AccountError

class DummyAccountRepository : AccountRepository {

    private val accounts = arrayListOf(
        Account(1, "Mandiri", 5_000_000L, AccountType.MANDIRI),
        Account(2, "BRI", 7_000_000L, AccountType.BRI),
        Account(3, "BCA", 1_500_000L, AccountType.BCA),
        Account(4, "Cash", 200_000L, AccountType.CASH),
    )

    override suspend fun getAccounts(): Resource<List<Account>, AccountError> =
        Resource.Success(accounts)
}