package com.akhris.domain.core.application

import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher

open class RemoveEntity<ID, ENTITY : IEntity<ID>>(
    private val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<ENTITY, RemoveEntity.Params<ENTITY>>(ioDispatcher) {

    override suspend fun run(params: Params<ENTITY>): ENTITY {
        return when (params) {
            is Remove -> remove(params.entityToRemove)
        }
    }

    private suspend fun remove(entity: ENTITY): ENTITY {
        repo.remove(entity)
        return entity
    }

    sealed class Params<T>
    data class Remove<E>(val entityToRemove: E) : Params<E>()

}