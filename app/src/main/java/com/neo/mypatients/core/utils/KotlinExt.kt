package com.neo.mypatients.core.utils

import com.neo.mypatients.BuildConfig

fun Exception.printDebugStackTrace() {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
}