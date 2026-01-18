package com.example.shared.navigation.runtime

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.shared.navigation.core.AppNavKey
import com.example.shared.navigation.core.NavResultBus
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass


/**
 * navigation3 Navigator 封装
 *
 * ❗职责非常单一：
 * - 操作 backStack
 * - 不参与 UI
 * - 不关心 Composable
 */
class Nav3Navigator(
    private val backStack: NavBackStack<NavKey>,
    private val resultBus: NavResultBus = NavResultBus()
) : Navigator {

    override fun navigate(key: AppNavKey) {
        backStack.add(key)
    }

    override fun back() {
       backStack.removeLastOrNull()
    }

    override fun replace(key: AppNavKey) {
        backStack.clear()
        backStack.add(key)
    }

    override fun <T : Any> setResult(result: T) {
        resultBus.publish(result)
        back()
    }

    fun <T : Any> observeResult(clazz: KClass<T>): Flow<T> =
        resultBus.observe(clazz)




}
