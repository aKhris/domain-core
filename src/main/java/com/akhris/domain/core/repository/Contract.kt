package com.akhris.domain.core.repository

import com.akhris.domain.core.entities.IEntity

interface IRepository<ID, ENTITY : IEntity<ID>> {
    suspend fun getByID(id: ID): ENTITY
    suspend fun remove(t: ENTITY)
    suspend fun update(t: ENTITY)
    suspend fun insert(t: ENTITY)
}