package com.example.lib_kmp.domain.mapper

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.lib_kmp.mvi.contract.PageStatus

fun LazyPagingItems<*>.toPageState(): PageStatus {
    val refresh = loadState.refresh

    return when {
        refresh is LoadState.Loading -> PageStatus.Loading

        refresh is LoadState.Error -> PageStatus.Error(
            refresh.error.message ?: "加载失败"
        )

        refresh is LoadState.NotLoading && itemCount == 0 ->
            PageStatus.Empty

        refresh is LoadState.NotLoading ->
            PageStatus.Success

        else -> PageStatus.Idle
    }
}
