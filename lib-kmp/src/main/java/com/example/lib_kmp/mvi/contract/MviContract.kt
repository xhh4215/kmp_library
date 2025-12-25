package com.example.lib_kmp.mvi.contract

import com.luanxh.mvi.core.data.PageData

/***
 * 定义了一个通用的契约
 */
object MviContract {


    sealed interface ShellIntent : MviIntent {
        object Enter : ShellIntent
        object Retry : ShellIntent
    }

    data class ViewState<T>(
        val pageStatus: PageStatus = PageStatus.Idle,
        val data: PageData<T>? = null
    ) : MviState

    sealed interface Effect : MviEffect {
        data class Toast(val msg: String) : Effect
        data class Navigate(val route: String) : Effect
        object ClosePage : Effect
    }
}

