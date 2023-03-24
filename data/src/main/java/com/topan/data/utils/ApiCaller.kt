package com.topan.data.utils

import com.topan.domain.utils.ResultCall
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

/**
 * Created by Topan E on 25/03/23.
 */
suspend fun <T> call(apiCall: suspend () -> Response<T>): ResultCall<T> {
    return try {
        val result = apiCall()
        if (result.isSuccessful) return ResultCall.Success(result.body()!!)
        ResultCall.Failed("${result.errorBody()}")
    } catch (exc: Exception) {
        getErrorDescription(exc)
    }
}

private fun getErrorDescription(exc: Exception): ResultCall.Failed = when (exc) {
    is SocketTimeoutException -> ResultCall.Failed("Connection timeout")
    is CancellationException,
    is UnknownHostException,
    is ConnectException -> ResultCall.Failed("Please check your network connection")
    else -> ResultCall.Failed("Error occurred, please try again later")
}
