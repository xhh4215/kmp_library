package com.luanxh.shared.mvi.controller

import com.example.shared.mvi.domain.data.DataResult
import com.example.shared.mvi.contract.BlockState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

