package com.andresuryana.budgettrack.domain.repository.user

import com.andresuryana.budgettrack.domain.model.user.User
import com.andresuryana.budgettrack.domain.resource.Resource
import com.andresuryana.budgettrack.domain.resource.error.UserError

interface UserRepository {

    suspend fun getActiveUser(): Resource<User, UserError>
}