package com.example.lib_kmp.mvi.contract

/***
 * 定义页面的UI状态
 */
sealed interface PageStatus {
    object Idle : PageStatus
    object Loading : PageStatus
    object Empty : PageStatus
    data class Error(val message: String) : PageStatus
    object Success : PageStatus
}

sealed interface ShellState : MviState {
    object Idle : ShellState
    object Checking : ShellState        // 页面进入前校验
    object Ready : ShellState           // 页面可展示
    data class Error(val msg: String) : ShellState
}

