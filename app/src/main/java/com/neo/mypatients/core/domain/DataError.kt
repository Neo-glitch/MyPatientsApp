package com.neo.mypatients.core.domain

import kotlin.Error

sealed interface DataError: AppError {

    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        DATABASE_ERROR,
        UNKNOWN
    }
}