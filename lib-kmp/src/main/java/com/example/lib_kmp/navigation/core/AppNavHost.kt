package com.example.lib_kmp.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.luanxh.navigation.runtime.LocalNavigator
import com.luanxh.navigation.runtime.Nav3Navigator

@Composable
fun AppNavHost(
    startKey: AppNavKey,
    registry: NavRegistry

) {
    val backStack = rememberNavBackStack(startKey)
    val navigator = remember(backStack) { Nav3Navigator(backStack) }


CompositionLocalProvider(LocalNavigator provides navigator) {
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            installRegistry(registry)
        }
    )
}

}
