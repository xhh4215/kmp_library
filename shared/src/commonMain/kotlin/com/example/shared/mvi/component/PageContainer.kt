package com.example.shared.mvi.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.shared.mvi.contract.BlockState
import com.example.shared.mvi.contract.PageState
import com.example.shared.mvi.contract.ShellState
import com.example.shared.mvi.controller.BlockSlots

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


/***
 *   数据块UI状态控制
 */
@Composable
fun <T> BlockContainer(
    state: BlockState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    slots: BlockSlots<T>,
) {
    Box(modifier) {
        when (state) {
            BlockState.Idle -> slots.idle()
            BlockState.Loading -> slots.loading()
            BlockState.Empty -> slots.empty()
            is BlockState.Error -> slots.error(state.message, onRetry)
            is BlockState.Success -> slots.content(state.data)
        }


    }
}



