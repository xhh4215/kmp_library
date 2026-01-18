package com.example.shared.mvi.store

import com.example.shared.mvi.controller.BlockController
import com.example.shared.mvi.controller.BlockSpec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/***
 * 数据块管理
 */
open class BaseStore(
    private val scope: CoroutineScope
) {

    private val blocks = mutableMapOf<String, BlockController<*>>()

    fun <T> block(
        key: String,
        spec: BlockSpec<T>
    ): BlockController<T> {
        val controller = BlockController(
            loader = spec.loader,
            errorMsg = spec.errorMsg,
            scope = scope
        )
        if (spec.autoLoad) {
            controller.load()
        }
        blocks[key] = controller
        return controller
    }

    /** 可选统一 retry */
    fun retry(key: String) {
        (blocks[key] as? BlockController<Any>)?.retry()
    }

    /** 页面退出 / 生命周期结束 */
    open fun clear() {
        scope.cancel()
    }
}