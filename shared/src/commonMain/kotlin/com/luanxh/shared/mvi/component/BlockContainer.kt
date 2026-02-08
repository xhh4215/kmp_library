package com.luanxh.shared.mvi.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.shared.mvi.contract.BlockState
import com.example.shared.mvi.controller.BlockSlots


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



