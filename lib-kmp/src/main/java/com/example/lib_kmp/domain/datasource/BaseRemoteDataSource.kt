package com.luanxh.mvi.core.domain.remote

import com.luanxh.mvi.core.data.DataResult
import com.luanxh.mvi.core.network.ApiResponse
import com.luanxh.mvi.core.network.engine.NetworkEngine
import com.luanxh.mvi.core.network.engine.executeApi

/***
 * @author 栾桂明
 * @see 一个抽象的数据请求数据源的抽象
 * 将网路请求返回的api数据通过扩展返回妆化为DataResult
 */
abstract class BaseRemoteDataSource(
    protected val engine: NetworkEngine
) {
    /***
     * api请求返回的指定数据类型
     */
    protected suspend fun <T> safeApi(call: suspend () -> T): DataResult<T> =
        engine.execute(call)

    /***
     * api返回的是封装好的 code message data 的数据类型
     */
    protected suspend fun <T> safeApiResponse(call: suspend () -> ApiResponse<T>): DataResult<T> =
        //通过这个方法请求返回的api数据转化为DataResult数据
        engine.executeApi(call)
}


