package com.akhris.domain.core.exceptions

/**
 * Exception is thrown when object was not found in repository
 */
class NotFoundInRepositoryException(what: String = "", repository: String = "") : RuntimeException() {
    override val message: String = "$what not found in repository $repository"
}