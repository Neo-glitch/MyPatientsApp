package com.neo.mypatients.core.data.network

import com.neo.mypatients.core.utils.K.TEST_NETWORK_HOST_NAME
import com.neo.mypatients.core.utils.K.TEST_NETWORK_PORT
import com.neo.mypatients.core.utils.K.TEST_NETWORK_TIMER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class NetworkConnectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        isConnected {
                throw NoConnectivityException()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())

    }

    private fun isConnected(emit: () -> Unit){
        try {
            val sock = Socket()
            sock.connect(
                InetSocketAddress(TEST_NETWORK_HOST_NAME, TEST_NETWORK_PORT),
                TEST_NETWORK_TIMER)
            sock.close()

        } catch (e: Exception) {
            emit.invoke()
        }

    }

}

class NoConnectivityException : IOException() {

}