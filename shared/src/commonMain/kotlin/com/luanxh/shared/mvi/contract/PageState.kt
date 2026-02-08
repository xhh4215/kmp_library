package com.luanxh.shared.mvi.contract

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



