package com.luanxh.shared.mvi.domain.data

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()

    data class Error(
        val code: Int? = null,
        val msg: String,
        val throwable: Throwable? = null
    ) : DataResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)

        fun error(code: Int? = null, msg: String, throwable: Throwable? = null) = Error(code, msg, throwable)
    }
}