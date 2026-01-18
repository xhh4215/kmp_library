package com.example.shared.network.engine

import com.example.shared.domain.data.DataResult
import com.example.shared.network.ApiService

class KtorEngine(
 ) : NetworkEngine {

    override suspend fun <T> execute(block: suspend () -> T): DataResult<T> {
        return runCatching {
            DataResult.Success(block())
        }.getOrElse {
            DataResult.Error(null, it.message ?: "网络异常", it)
        }
    }

}