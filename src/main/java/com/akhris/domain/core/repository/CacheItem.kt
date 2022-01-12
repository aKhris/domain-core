package com.akhris.domain.core.repository

/**
 * Cache item containing cached object: [item] and time when it was cached: [timeStamp]
 */
data class CacheItem<T : Any>(val item: T, val timeStamp: Long)
