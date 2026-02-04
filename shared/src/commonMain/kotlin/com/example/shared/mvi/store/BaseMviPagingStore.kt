package com.example.shared.mvi.store

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest



/**
 * 分页数据块控制器
 * @param scope 生命周期作用域，一般是 viewModelScope
 * @param paramFlow 分页参数 StateFlow
 * @param createPagingSource param -> PagingSource
 */
class SinglePagingState<UI : Any, PARAM : Any>(
    private val scope: CoroutineScope,
    private val paramFlow: StateFlow<PARAM>,
    private val createPagingSource: (PARAM) -> PagingSource<Int, UI>,
    private val pageSize: (PARAM) -> Int = { 10 }
) {

    val pagingFlow: Flow<PagingData<UI>> = paramFlow
        .flatMapLatest { param ->
            Pager(
                config = PagingConfig(
                    pageSize = pageSize(param),
                    initialLoadSize = pageSize(param)
                ),
                pagingSourceFactory = { createPagingSource(param) }
            ).flow
        }
        .cachedIn(scope)

}