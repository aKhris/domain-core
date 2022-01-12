package com.akhris.domain.core.repository

import com.akhris.domain.core.entities.IEntity
import com.akhris.domain.core.exceptions.NotFoundInRepositoryException
import kotlinx.coroutines.flow.SharedFlow

/**
 * Base repository class with in-memory caching feature and repository callbacks.
 * need to override:
 * [fetchItemFromRepo] - fetching item from repository
 * [removeFromRepo] - removing item from repository
 * [updateInRepo] - updating item in repository
 * [insertInRepo] - inserting new item in repository
 */
abstract class BaseCachedRepository<ID : Any, ENTITY : IEntity<ID>>(maxCacheCapacity: Int = 10) : IRepository<ID, ENTITY>,
    IRepositoryCallback<ENTITY> {

    private val repoCache: RepositoryCache<ID, ENTITY> = RepositoryCache(maxCapacity = maxCacheCapacity)
    private val repoCallbacks: RepositoryCallbacks<ENTITY> = RepositoryCallbacks()

    override val updates: SharedFlow<RepoResult<ENTITY>> = repoCallbacks.updates

    override suspend fun getByID(id: ID): ENTITY {
        return repoCache.getFromCacheOrFetch(id = id, fetcher = { idToFetch ->
            fetchItemFromRepo(idToFetch) ?: throw NotFoundInRepositoryException(
                "Entity not found for id: $id",
                repository = this.toString()
            )
        })
    }

    abstract suspend fun fetchItemFromRepo(idToFetch: ID): ENTITY?
    abstract suspend fun removeFromRepo(entity: ENTITY)
    abstract suspend fun updateInRepo(entity: ENTITY)
    abstract suspend fun insertInRepo(entity: ENTITY)

    override suspend fun remove(t: ENTITY) {
        repoCache.remove(t.id)
        removeFromRepo(t)
        repoCallbacks.onItemRemoved(t)
    }

    override suspend fun update(t: ENTITY) {
        repoCache.insert(t)
        updateInRepo(t)
        repoCallbacks.onItemUpdated(t)
    }

    override suspend fun insert(t: ENTITY) {
        repoCache.insert(t)
        insertInRepo(t)
        repoCallbacks.onItemInserted(t)
    }
}