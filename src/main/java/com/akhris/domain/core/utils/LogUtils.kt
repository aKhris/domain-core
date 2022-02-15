package com.akhris.domain.core.utils

import com.akhris.domain.core.utils.LogUtils.isLogEnabled


object LogUtils {
    var isLogEnabled = false
}

fun Any.log(text: Any) {
    if (isLogEnabled)
        println("${this::class.simpleName}@${this.hashCode()}: $text")
}