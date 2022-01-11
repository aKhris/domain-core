package com.akhris.domain.core.utils

import com.akhris.domain.core.application.Result
import com.akhris.domain.core.exceptions.NotFoundInRepositoryException

/**
 * If result is [Result.Success] - returns result value, if not - return null.
 */
fun <T> Result<T>.unpack(): T? = when (this) {
    is Result.Success -> value
    else -> null
}

/**
 * If result is [Result.Success] - returns result value, if not - throws exception.
 */
@Throws(Throwable::class)
fun <T> Result<T>.unpack(whatNotFoundInRepo: String): T = when (this) {
    is Result.Success -> value
    is Result.Failure -> throw throwable
}
