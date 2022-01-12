package com.akhris.domain.core.application

import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher


/**
 * Base use case to get [IEntity] from [IRepository] by ID.
 * May be overridden for more complex use cases.
 */
open class GetEntity<ID, ENTITY : IEntity<ID>>(
    private val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<ENTITY, GetEntity.Params<ID>>(ioDispatcher) {

    override suspend fun run(params: Params<ID>): ENTITY {
        return when (params) {
            is GetByID -> getByID(params.id)
        }
    }

    private suspend fun getByID(id: ID): ENTITY {
        return repo.getByID(id)
    }

    sealed class Params<T>
    data class GetByID<ID>(val id: ID) : Params<ID>()

}