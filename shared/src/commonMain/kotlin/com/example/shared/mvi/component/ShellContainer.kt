package com.example.shared.mvi.component

import androidx.compose.runtime.Composable
import com.example.shared.mvi.contract.ShellState

/***
 * 页面UI状态控制
 */
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
