package com.akhris.domain.core.application

import com.akhris.domain.core.application.Result
import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher


/**
 * Base use case to update [IEntity] in [IRepository].
 * May be overridden for more complex use cases.
 */
open class UpdateEntity<ID, ENTITY : IEntity<ID>>(
    private val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<ENTITY, UpdateEntity.Params>(ioDispatcher) {

    override suspend fun run(params: Params): ENTITY {
        return when (params) {
            is Update -> (params.entityToUpdate as? ENTITY)?.let {
                update(it)
            } ?: throw IllegalArgumentException("Entity to update is not type of updater use case: $this")
        }
    }

    private suspend fun update(entity: ENTITY): ENTITY {
        repo.update(entity)
        return entity
    }

    sealed class Params
    data class Update(val entityToUpdate: Any) : Params()

}