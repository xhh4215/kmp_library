package com.example.shared.mvi.store

import com.example.shared.mvi.domain.data.DataResult
import com.example.shared.mvi.contract.MviEffect
import com.example.shared.mvi.contract.MviIntent
import com.example.shared.mvi.contract.MviState
import com.example.shared.mvi.controller.BlockController
import com.example.shared.mvi.controller.BlockSpec
import kotlinx.coroutines.CoroutineScope

abstract class BaseMviBlockStore<
        I : MviIntent,
        S : MviState,
        E : MviEffect
        >(
    scope: CoroutineScope
) : BaseMviStore<I, S, E>(scope) {

    // 内置 Block / Paging 管理
    protected fun <T> block(
        key: String,
        loader: suspend () -> DataResult<T>,
        errorMsg: String
    ): BlockController<T> {
        return BaseStore(scope).block(
            key = key,
            spec = BlockSpec(loader, errorMsg)
        )
    }

}

/***
 * class UserPageStore(scope: CoroutineScope, private val repo: UserRepo) :
 *     BaseMviBlockStore<MviIntent, ShellState, MviEffect>(scope) {
 *
 *     val userBlock = block(
 *         key = "user",
 *         loader = { repo.getUser() },
 *         errorMsg = "加载用户失败"
 *     )
 *
 *     val postsBlock = block(
 *         key = "posts",
 *         loader = { repo.getPosts() },
 *         errorMsg = "加载帖子失败"
 *     )
 *
 *     override fun initialState(): ShellState = ShellState.Idle
 *
 *     override fun handleIntent(intent: MviIntent) {
 *         // 页面行为分发逻辑
 *     }
 * }
 */