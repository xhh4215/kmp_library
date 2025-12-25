package com.example.lib_kmp.mvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_kmp.mvi.contract.MviEffect
import com.example.lib_kmp.mvi.contract.MviIntent
import com.example.lib_kmp.mvi.contract.MviState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/***
 * @author 栾桂明
 * @date 2025年12月2日
 * @desc  对使用的ViewModel的逻辑基本封装
 */
abstract class BaseMviViewModel<
        I : MviIntent,
        S : MviState,
        E : MviEffect
        > : ViewModel() {
    //当前的页面的最初的状态的实现逻辑 这个不同的页面的逻辑可能是不同的所以是一个抽象方法
    protected abstract fun initialState(): S

    //存储的是当前的ViewModel 内部存储的数据
    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<S> = _state.asStateFlow()

    //实现通过ViewModel实现通知
    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    /***
     *  页面行为的分发入口方法
     */
    fun dispatch(intent: I) {
        handleIntent(intent)
    }

    /***
     * 具体的行为意图的分发 不同的viewModel有他自己的分发逻辑
     */
    protected abstract fun handleIntent(intent: I)

    /** 切换 State（标准方式） */
    protected fun setState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    protected fun reduce(block: S.() -> S) = setState(block)


    /** 发射 Effect（一次性事件） */
    protected fun postEffect(effect: E) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}
