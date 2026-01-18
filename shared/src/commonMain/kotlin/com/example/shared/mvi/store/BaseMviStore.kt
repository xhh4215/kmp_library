package com.example.shared.mvi.store

import com.example.shared.mvi.contract.MviEffect
import com.example.shared.mvi.contract.MviIntent
import com.example.shared.mvi.contract.MviState
import com.example.shared.mvi.controller.loadFrom
import com.example.shared.mvi.controller.loadListFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/***
 * 页面状态的管理
 */
abstract class BaseMviStore<
        I : MviIntent,
        S : MviState,
        E : MviEffect
        >(
    coroutineScope: CoroutineScope
) {

    protected val scope = coroutineScope

    protected abstract fun initialState(): S

    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun dispatch(intent: I) {
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: I)

    protected fun setState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    protected fun postEffect(effect: E) {
        scope.launch {
            _effect.send(effect)
        }
    }
}