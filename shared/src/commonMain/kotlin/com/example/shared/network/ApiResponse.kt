package com.example.shared.network

 import com.example.shared.domain.data.DataResult
 import com.example.shared.network.engine.NetworkEngine

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data:T?
) {
    fun isSuccess()=code==200
}

