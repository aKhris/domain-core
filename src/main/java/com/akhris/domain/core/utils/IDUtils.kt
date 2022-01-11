package com.akhris.domain.core.utils

import java.util.*

object IDUtils {
    /**
     * Makes new unique string id
     */
    fun newID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Convert id to Long (loosing bits)
     */
    fun toLong(id: String): Long {
        return (UUID.fromString(id).mostSignificantBits and Long.MAX_VALUE)
    }

    /**
     * Convert string id to int using hashcode() function
     */
    fun toInt(id: String): Int {
        return id.hashCode()
    }
}