package com.topan.data.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Topan E on 25/03/23.
 */
object OkHttpHelper {
    private const val NETWORK_TIMEOUT = 45L

    fun create() = OkHttpClient.Builder()
        .addInterceptor(getLogInterceptor())
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    private fun getLogInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
