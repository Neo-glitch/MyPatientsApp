package com.neo.mypatients.core.utils

import com.neo.mypatients.R
import com.neo.mypatients.core.domain.DataError

fun DataError.toUiText(): Int {
    val stringRes = when(this) {
        DataError.Local.DATABASE_ERROR -> R.string.error_database
        DataError.Local.UNKNOWN -> R.string.error_local_unknown
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.SERVER -> R.string.error_server
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
        DataError.Remote.UNKNOWN -> R.string.error_remote_unknown
    }
    return stringRes
}