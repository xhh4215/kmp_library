package com.luanxh.mvi.core.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.lib_kmp.mvi.core.BaseMviViewModel
import com.luanxh.mvi.core.contract.MviContract
import com.luanxh.mvi.core.contract.ShellState
import kotlinx.coroutines.launch

abstract class PageShellViewModel :
    BaseMviViewModel<
            MviContract.ShellIntent,
            ShellState,
            MviContract.Effect
            >() {

    override fun initialState(): ShellState = ShellState.Idle

    override fun handleIntent(intent: MviContract.ShellIntent) {
        when (intent) {
            MviContract.ShellIntent.Enter -> checkPage()
            MviContract.ShellIntent.Retry -> checkPage()
        }
    }

    protected fun setChecking() {
        setState { ShellState.Checking }
    }

    protected fun setReady() {
        setState { ShellState.Ready }
    }

    protected fun setError(msg: String) {
        setState { ShellState.Error(msg) }
    }

    /**
     * 子类只实现：页面是否可进入
     */
    protected abstract suspend fun check(): Boolean

    private fun checkPage() {
        setChecking()
        viewModelScope.launch {
            try {
                val ok = check()
                if (ok) setReady()
                else setError("页面不可访问")
            } catch (e: Exception) {
                setError(e.message ?: "页面初始化失败")
            }
        }
    }
}
