package com.akhris.domain.core.application

import com.akhris.domain.core.di.IoDispatcher
import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.repository.IRepository
import com.akhris.domain.core.repository.ISpecification
import kotlinx.coroutines.CoroutineDispatcher

class GetEntities<ID, ENTITY : IEntity<ID>>(
    val repo: IRepository<ID, ENTITY>,
    @IoDispatcher
    ioDispatcher: CoroutineDispatcher
) : UseCase<List<ENTITY>, GetEntities.Params>(ioDispatcher) {

    override suspend fun run(params: Params): List<ENTITY> {
        return when (params) {
            is GetBySpecification -> repo.query(params.specification)
        }
    }

    sealed class Params
    data class GetBySpecification(val specification: ISpecification) : Params()
}
