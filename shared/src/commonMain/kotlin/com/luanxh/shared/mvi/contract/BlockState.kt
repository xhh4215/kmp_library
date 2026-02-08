package com.luanxh.shared.mvi.contract

/***
 * 页面中的数据块加载的状态的定义
 */
sealed interface BlockState<out T> : MviState {
    object Idle : BlockState<Nothing>
    object Loading : BlockState<Nothing>
    data object Empty : BlockState<Nothing>
    data class Success<T>(val data: T) : BlockState<T>
    data class Error(val message: String) : BlockState<Nothing>
}