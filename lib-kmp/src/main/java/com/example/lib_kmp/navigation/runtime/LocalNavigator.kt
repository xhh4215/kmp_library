package com.luanxh.navigation.runtime

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * 当前 NavController / Navigator 的 CompositionLocal
 * 必须在 AppNavHost 提供
 */
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error(
        "LocalNavigator not provided. " +
                "Did you forget to wrap your NavDisplay with AppNavHost?"
    )
}
