package com.andresuryana.budgettrack.domain.resource

import com.andresuryana.budgettrack.domain.resource.error.ResourceError

sealed interface Resource<out D, out E : ResourceError> {
    data class Success<out D, out E : ResourceError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : ResourceError>(val error: E) : Resource<D, E>
}