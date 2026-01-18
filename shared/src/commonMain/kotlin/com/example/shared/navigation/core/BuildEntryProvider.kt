package com.example.shared.navigation.core

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.window.Dialog
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.shared.navigation.runtime.LocalNavigator




fun EntryProviderScope<NavKey>.installRegistry(
    registry: NavRegistry
) {
    registry.all().forEach { destination ->
        @Suppress("UNCHECKED_CAST")
        installInternal(
            destination as NavDestination<AppNavKey>
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun EntryProviderScope<NavKey>.installInternal(
    destination: NavDestination<AppNavKey>
) {
    entry<AppNavKey> { key ->

        if (key::class != destination.keyClass) {
            // 不是这个页面，交给 fallback
            throw IllegalStateException("Unknown key $key")
        }

        val navigator = LocalNavigator.current

        val typeKey = key as AppNavKey

        when (destination.style) {

            MavPageStyle.Screen -> {
                destination.content(typeKey)
            }

            MavPageStyle.Dialog -> {
                Dialog(onDismissRequest = { navigator.back() }) {
                    destination.content(typeKey)
                }
            }

            MavPageStyle.BottomSheet -> {
                ModalBottomSheet(onDismissRequest = { navigator.back() }) {
                    destination.content(typeKey)
                }
            }
        }
    }
}

