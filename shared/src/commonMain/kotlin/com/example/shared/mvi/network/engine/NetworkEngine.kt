package com.example.shared.mvi.network.engine

 import com.example.shared.mvi.domain.data.DataResult
 import com.example.shared.mvi.network.ApiResponse

/***
 * @author 栾桂明
 * @desc 封装一个统一的发起网络请求的入口
 */
interface NetworkEngine {
    suspend fun <T> execute(block: suspend () -> T): DataResult<T>
}

suspend inline fun <T> NetworkEngine.executeApi(
    crossinline block: suspend () -> ApiResponse<T>
): DataResult<T> {
    return try {
        val response = block()
        if (response.isSuccess() && response.data != null) {
            DataResult.Success(response.data)
        } else {
            DataResult.Error(response.code, response.msg)
        }
    } catch (e: Exception) {
        DataResult.Error(null, e.message ?: "网络异常", e)
    }
}

