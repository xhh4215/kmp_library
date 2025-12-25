package com.example.lib_kmp.navigation.core

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

data class NavDestination<T : AppNavKey>(
    val keyClass: KClass<T>,
    val style: MavPageStyle = MavPageStyle.Screen,
    val content: @Composable (T) -> Unit
)
