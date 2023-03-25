package com.topan.data.utils

import retrofit2.Retrofit

/**
 * Created by Topan E on 25/03/23.
 */
object ServiceHelper {
    inline fun <reified T> create(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }
}
