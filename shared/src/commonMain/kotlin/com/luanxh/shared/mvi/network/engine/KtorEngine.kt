package com.luanxh.shared.mvi.network.engine

import com.example.shared.mvi.domain.data.DataResult

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