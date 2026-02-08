package com.luanxh.shared.mvi.contract


/***
 * 定义了一个通用的契约
 */
object MviContract {


    sealed interface ShellIntent : MviIntent {
        object Enter : ShellIntent
        object Retry : ShellIntent
    }

    data class ViewState<T>(
        val pageState: PageState = PageState.Idle,
     ) : MviState

    sealed interface Effect : MviEffect {
        data class Toast(val msg: String) : Effect
        data class Navigate(val route: String) : Effect
        object ClosePage : Effect
    }
}

