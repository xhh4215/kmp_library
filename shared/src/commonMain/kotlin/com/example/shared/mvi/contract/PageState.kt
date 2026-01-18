package com.example.shared.mvi.contract

/***
 * 定义页面的UI状态
 */
sealed interface PageState {
    object Idle : PageState
    object Loading : PageState
    object Empty : PageState
    data class Error(val message: String) : PageState
    object Success : PageState
}

sealed interface ShellState : MviState {
    object Idle : ShellState
    object Checking : ShellState        // 页面进入前校验
    object Ready : ShellState           // 页面可展示
    data class Error(val msg: String) : ShellState
}

