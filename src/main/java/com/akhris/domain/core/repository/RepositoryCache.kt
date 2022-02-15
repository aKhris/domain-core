package com.akhris.domain.core.repository

import com.akhris.domain.core.entities.IEntity
import java.util.concurrent.ConcurrentHashMap

class RepositoryCache<PRIMARY_KEY : Any, T : IEntity<PRIMARY_KEY>>(private val maxCapacity: Int = 10) {

    private val cache: ConcurrentHashMap<PRIMARY_KEY, CacheItem<T>> = ConcurrentHashMap(maxCapacity)

    suspend fun getFromCacheOrFetch(id: PRIMARY_KEY, fetcher: suspend (PRIMARY_KEY) -> T): T {
        val cacheItem = cache[id]
        return if (cacheItem == null) {
//            println("not found item for id: $id in cache, fetching from repo...")
            val fetched = fetcher(id)
            putKeepingMaxCapacity(fetched)
            fetched
        } else {
            cacheItem.item
        }
    }

    fun insert(item: T) {
//        println("updating item (id: ${item.id}) in cache")
        putKeepingMaxCapacity(item)
    }

    fun remove(id: PRIMARY_KEY) {
//        println("removing item (id: $id) from cache")
        cache.remove(id)
    }

    private fun putKeepingMaxCapacity(item: T) {
//        println("put item: $item in cache")
        cache[item.id] = CacheItem(item = item, timeStamp = System.currentTimeMillis())
        if (cache.size > maxCapacity) {
            //remove the oldest item
            val oldestKey =
                cache
                    .entries
                    .sortedBy { i -> i.value.timeStamp }[0]
                    .key

//            println("cache size > $maxCapacity, removing the oldest item with id: $oldestKey")
            remove(oldestKey)
        }
    }


    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("CacheRepository: ${this.javaClass.simpleName}")
        stringBuilder.appendLine("size: ${cache.size}")
        cache
            .forEach { entry ->
                stringBuilder.appendLine(
                    "${entry.key}\t\t-\t\t${entry.value}"
                )
            }
        stringBuilder.appendLine()
        return stringBuilder.toString()
    }
}