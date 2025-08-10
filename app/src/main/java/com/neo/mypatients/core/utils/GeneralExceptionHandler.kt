package com.neo.mypatients.core.utils

import android.database.sqlite.SQLiteException
import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.data.network.NoConnectivityException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.UnknownHostException

object GeneralExceptionHandler {

    fun getNetworkError(throwable: Throwable): DataError.Remote {
        throwable.printStackTrace()
        return when(throwable) {
            is CancellationException -> throw throwable
            is HttpException -> DataError.Remote.SERVER
            is NoConnectivityException -> DataError.Remote.NO_INTERNET
            is UnknownHostException -> DataError.Remote.UNKNOWN
            is InterruptedIOException -> DataError.Remote.REQUEST_TIMEOUT
            is SocketException -> DataError.Remote.REQUEST_TIMEOUT
            else -> {
                throwable.printStackTrace()
                DataError.Remote.UNKNOWN
            }
        }
    }

    fun getLocalError(throwable: Throwable): DataError.Local {
        return when(throwable) {
            is SQLiteException, is IllegalStateException -> {
                DataError.Local.DATABASE_ERROR
            }
            else -> {
                throwable.printStackTrace()
                DataError.Local.UNKNOWN
            }
        }
    }
}