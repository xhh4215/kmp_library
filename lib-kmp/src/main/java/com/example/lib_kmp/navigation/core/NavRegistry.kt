package com.example.lib_kmp.navigation.core

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

class NavRegistry {

    private val destinations = mutableListOf<NavDestination<out AppNavKey>>()

    fun <T : AppNavKey> register(
        keyClass: KClass<T>,
        style: MavPageStyle = MavPageStyle.Screen,
        content: @Composable (T) -> Unit
    ) {
        destinations += NavDestination(keyClass, style, content)
    }

    internal fun all(): List<NavDestination<out AppNavKey>> = destinations
}
