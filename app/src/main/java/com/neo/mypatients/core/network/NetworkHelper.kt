package com.neo.mypatients.core.network

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.GeneralExceptionHandler
import kotlin.coroutines.cancellation.CancellationException


object NetworkHelper {

    suspend fun <T> handleApiCall(apiCall: suspend () -> T): Resource<T, DataError.Remote> {
        return try {
            val response = apiCall()
            Resource.Success(response)
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) throw throwable
            Resource.Error(GeneralExceptionHandler.getNetworkError(throwable))
        }
    }

}