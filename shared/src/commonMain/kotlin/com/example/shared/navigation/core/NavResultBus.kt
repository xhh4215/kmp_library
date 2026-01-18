package com.example.shared.navigation.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass
class NavResultBus {

    private val flow = MutableSharedFlow<NavResultEvent<Any>>(
        replay = 0,
        extraBufferCapacity = 1
    )

    fun <T : Any> publish(result: T) {
        flow.tryEmit(
            NavResultEvent(payload = result)
        )
    }

    fun <T : Any> observe(clazz: KClass<T>): Flow<T> =
        flow
            .filter { clazz.isInstance(it.payload) }
            .map { it.payload as T }
}
