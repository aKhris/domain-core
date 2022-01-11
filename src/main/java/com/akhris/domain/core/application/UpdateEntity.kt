package com.akhris.domain.core.application

import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher

open class UpdateEntity<ID, ENTITY : IEntity<ID>>(
    private val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<ENTITY, UpdateEntity.Params<ENTITY>>(ioDispatcher) {

    override suspend fun run(params: Params<ENTITY>): ENTITY {
        return when (params) {
            is Update -> update(params.entityToUpdate)
        }
    }

    private suspend fun update(entity: ENTITY): ENTITY {
        repo.update(entity)
        return entity
    }

    sealed class Params<T>
    data class Update<E>(val entityToUpdate: E) : Params<E>()

}