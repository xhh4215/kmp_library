package com.luanxh.shared.mvi.domain.mapper

import androidx.paging.LoadState
 import androidx.paging.compose.LazyPagingItems
import com.example.shared.mvi.contract.PagingState

fun LazyPagingItems<*>.toPagingState(): PagingState {
    return when (val refresh = loadState.refresh) {
        is LoadState.Loading -> PagingState.Loading
        is LoadState.Error -> PagingState.Error(
            refresh.error.message ?: "加载失败"
        )

        is LoadState.NotLoading if itemCount == 0 ->
            PagingState.Empty

        is LoadState.NotLoading -> PagingState.Success
        else -> PagingState.Idle
    }
}
