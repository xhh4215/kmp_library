package com.example.shared.mvi.controller

import com.example.shared.domain.data.DataResult
import com.example.shared.mvi.contract.BlockState
import com.example.shared.mvi.contract.PagingBlockState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class BlockController<T>(
    private val loader: suspend () -> DataResult<T>,
    private val errorMsg: String,
    private val scope: CoroutineScope,
    autoLoad: Boolean = true
) {

    val _state = MutableStateFlow<BlockState<T>>(BlockState.Idle)
    val state: StateFlow<BlockState<T>> = _state

    init {
        if (autoLoad) {
            load()
        }
    }

    fun load() {
        scope.launch {
            _state.value = BlockState.Loading
            when (val result = loader()) {
                is DataResult.Success ->
                    _state.value = BlockState.Success(result.data)
                is DataResult.Error ->
                    _state.value = BlockState.Error(errorMsg)
            }
        }
    }

    fun retry() = load()

    fun reset() {
        _state.value = BlockState.Idle
    }
}

suspend fun <D, U> BlockController<U>.loadFrom(
    errorMsg: String,
    mapper: (D) -> U,
    loader: suspend () -> DataResult<D>
) {
    _state.value = BlockState.Loading
    when (val result = loader.invoke()) {
        is DataResult.Success -> {
            _state.value = BlockState.Success(mapper.invoke(result.data))
        }

        is DataResult.Error -> {
            _state.value = BlockState.Error(errorMsg)
        }
    }
}

suspend fun <D, U> BlockController<List<U>>.loadListFrom(
    errorMsg: String,
    mapper: (D) -> U,
    loader: suspend () -> DataResult<List<D>>
) {
    _state.value = BlockState.Loading
    when (val result = loader.invoke()) {
        is DataResult.Success -> {
            _state.value = BlockState.Success(result.data.map { mapper(it) })
        }

        is DataResult.Error -> {
            _state.value = BlockState.Error(errorMsg)
        }
    }
}

/**
 * 通用分页 Block
 * @param scope 生命周期作用域，一般是 viewModelScope
 * @param paramFlow 分页参数 StateFlow
 * @param createPagingSource param -> PagingSource
 */
class PagingBlock<UI : Any, PARAM : Any>(
    private val scope: CoroutineScope,
    private val paramFlow: StateFlow<PARAM>,
    private val createPagingSource: (PARAM) -> PagingSource<Int, UI>,
    private val pageSize: (PARAM) -> Int = { 10 }
) {

    private val _state = MutableStateFlow<PagingBlockState<UI>>(PagingBlockState.Idle)
    val state: StateFlow<PagingBlockState<UI>> = _state

    @OptIn(ExperimentalCoroutinesApi::class)
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

    fun refresh() {
        // 只要 paramFlow 更新，pagingFlow 会自动刷新
        // 如果需要手动刷新，可以考虑触发 paramFlow 重新发射
    }
}
