package com.example.lib_kmp.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.lib_kmp.mvi.contract.BlockState
import com.example.lib_kmp.mvi.contract.MviContract
import com.example.lib_kmp.mvi.contract.PageStatus
import com.example.lib_kmp.mvi.contract.ShellState

@Composable
fun ShellContainer(
    state: ShellState,
    onRetry: () -> Unit,
    checking: @Composable () -> Unit,
    error: @Composable (String, () -> Unit) -> Unit,
    content: @Composable () -> Unit
) {
    when (state) {
        ShellState.Idle -> Unit
        ShellState.Checking -> checking()
        ShellState.Ready -> content()
        is ShellState.Error -> error(state.msg, onRetry)
    }
}




@Composable
fun <T> BlockContainer(
    state: BlockState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    loading: @Composable () -> Unit = { },
    error: @Composable (msg: String, retry: () -> Unit) -> Unit = { msg, retry ->

    },
    empty: @Composable () -> Unit = { },
    content: @Composable (T) -> Unit
) {
    Box(modifier) {
        when (state) {
            BlockState.Idle -> Unit

            BlockState.Loading -> loading()

            is BlockState.Error ->
                error(state.message, onRetry)

            is BlockState.Success -> {
                val data = state.data
                when {
                    data == null -> empty()
                    data is Collection<*> && data.isEmpty() -> empty()
                    else -> content(data)
                }
            }
        }
    }
}


@Composable
fun <T : Any> MviPagingPageContainer(
    pagingItems: LazyPagingItems<T>,
    onRetry: () -> Unit,
    content: @Composable (LazyPagingItems<T>) -> Unit,
    loadingView: @Composable () -> Unit,
    emptyView: @Composable (retryAction: () -> Unit) -> Unit,
    errorView: @Composable (errorMessage: String, retryAction: () -> Unit) -> Unit
) {
    when (val status = pagingItems.toPageStatus()) {

        PageStatus.Idle -> Unit

        PageStatus.Loading -> loadingView()

        PageStatus.Empty -> emptyView(onRetry)

        is PageStatus.Error -> errorView(status.message, onRetry)

        PageStatus.Success -> content(pagingItems)
    }
}
