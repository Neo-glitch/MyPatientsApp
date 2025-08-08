package com.neo.mypatients.core.domain

sealed class Resource<out T, out E: AppError> {
    data class Success<out T>(val data: T): Resource<T, Nothing>()
    data class Error<out E: AppError>(val error: E): Resource<Nothing, E>()
}