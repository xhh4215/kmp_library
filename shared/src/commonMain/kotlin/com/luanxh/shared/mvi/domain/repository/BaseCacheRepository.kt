package com.luanxh.shared.mvi.domain.repository

import com.example.shared.mvi.domain.data.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseCacheRepository<T : Any> : BaseRepository() {
    /***
     * 读缓存
     */
    abstract fun getLocal(): Flow<T?>

    /***
     * 接口请求
     */
    abstract fun getRemote(): DataResult<T>

    /**
     * 写入缓存
     */
    abstract suspend fun saveRemoteData(remoteData: T)


    fun fetchData(): Flow<DataResult<T>> = flow {

//        emit(DataResult.Loading)

        getLocal().collect { localData ->
            if (localData != null) {
                emit(DataResult.Companion.success(localData))
            }
        }
        val remoteResult = getRemote()

        if (remoteResult is DataResult.Success) {
            saveRemoteData(remoteResult.data)
        }
        emit(remoteResult)
    }.catch { e ->
        emit(DataResult.Error(msg = e.message ?: "Unknown", throwable = e))
    }
}