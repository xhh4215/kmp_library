package com.example.shared.domain.mapper

import androidx.paging.LoadState
import com.example.shared.mvi.contract.PageState

fun LazyPagingItems<*>.toPageState(): PageState {
    return when (val refresh = loadState.refresh) {
        is LoadState.Loading -> PageState.Loading
        is LoadState.Error -> PageState.Error(
            refresh.error.message ?: "加载失败"
        )

        is LoadState.NotLoading if itemCount == 0 ->
            PageState.Empty

        is LoadState.NotLoading -> PageState.Success
    }
}
