package com.topan.domain.utils

/**
 * Created by Topan E on 25/03/23.
 */
sealed class ResultCall<out T> {
    data class Success<T>(val data: T) : ResultCall<T>()
    data class Failed(val errorMessage: String) : ResultCall<Nothing>()
}
