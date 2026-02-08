package com.luanxh.shared.mvi.network

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data:T?
) {
    fun isSuccess()=code==200
}

