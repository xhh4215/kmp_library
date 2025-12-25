package com.example.lib_kmp.network

import com.example.lib_kmp.domain.data.DataResult
import com.luanxh.mvi.core.network.engine.NetworkEngine

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data:T?
) {
    fun isSuccess()=code==200
}

suspend fun <T> NetworkEngine.executeApi(
    call: suspend () -> ApiResponse<T>
): DataResult<T> {
    return runCatching {
        val res = call()
        if (res.isSuccess() && res.data != null) {
            DataResult.Success(res.data)
        } else {
            DataResult.Error(res.code, res.msg)
        }
    }.getOrElse {
        DataResult.Error(null, it.message ?: "网络异常", it)
    }
}