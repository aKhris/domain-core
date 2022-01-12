package com.akhris.domain.core.entities

/**
 * Base interface for entity object.
 * [ID] - type of id ([String], [Long], [Int],...)
 */
interface IEntity<ID> {
    val id: ID
}