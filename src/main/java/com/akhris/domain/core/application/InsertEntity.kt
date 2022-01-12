package com.akhris.domain.core.application

import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base use case to insert [IEntity] to [IRepository].
 * May be overridden for more complex use cases.
 */
open class InsertEntity<ID, ENTITY : IEntity<ID>>(
    private val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<ENTITY, InsertEntity.Params<ENTITY>>(ioDispatcher) {

    override suspend fun run(params: Params<ENTITY>): ENTITY {
        return when (params) {
            is Update -> insert(params.entityToInsert)
        }
    }

    private suspend fun insert(entity: ENTITY): ENTITY {
        repo.insert(entity)
        return entity
    }

    sealed class Params<T>
    data class Update<E>(val entityToInsert: E) : Params<E>()

}