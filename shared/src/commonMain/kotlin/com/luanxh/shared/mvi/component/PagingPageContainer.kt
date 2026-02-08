package com.luanxh.shared.mvi.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.shared.mvi.contract.PageState
import com.example.shared.mvi.contract.PagingState
import com.example.shared.mvi.domain.mapper.toPagingState


@Composable
fun <T : Any> PagingPageContainer(
    pagingItems: LazyPagingItems<T>,
    onRetry: () -> Unit,
    content: @Composable (LazyPagingItems<T>) -> Unit,
    loadingView: @Composable () -> Unit,
    emptyView: @Composable (retryAction: () -> Unit) -> Unit,
    errorView: @Composable (errorMessage: String, retryAction: () -> Unit) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        content(pagingItems)
        when (val status = pagingItems.toPagingState()) {

            PagingState.Idle -> Unit

            PagingState.Loading -> loadingView()

            PagingState.Empty -> emptyView(onRetry)

            is PagingState.Error -> errorView(status.message, onRetry)

            PagingState.Success -> content(pagingItems)
        }
    }

}
