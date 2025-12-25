package com.luanxh.mvi.core.network.engine

import android.net.http.HttpException
import com.luanxh.mvi.core.data.DataResult
//
//class RetrofitEngine(
//) : NetworkEngine {
//
//    /***
//     * 具体的进行网络请求的实现逻辑
//     */
//    override suspend fun <T> execute(block: suspend () -> T): DataResult<T> {
//        return try {
//            DataResult.Success(block())
//        } catch (e: HttpException) {
//            DataResult.Error(e.code(), e.message())
//        } catch (e: Exception) {
//            DataResult.Error(null, e.message ?: "网络异常", e)
//        }
//    }
//
//
//}
