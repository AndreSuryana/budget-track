package com.andresuryana.budgettrack.data.dummy

import com.andresuryana.budgettrack.domain.model.user.User
import com.andresuryana.budgettrack.domain.repository.user.UserRepository
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.UserError

class DummyUserRepository : UserRepository {

    override suspend fun getActiveUser(): Resource<User, UserError> =
        Resource.Success(User(1, "Andre Suryana", null))
}