package com.example.lib_kmp.network.engine

import com.luanxh.mvi.core.data.DataResult
import com.luanxh.mvi.core.network.engine.NetworkEngine

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

