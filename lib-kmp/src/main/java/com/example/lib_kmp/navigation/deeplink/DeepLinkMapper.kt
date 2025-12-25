package com.luanxh.navigation.deeplink

import com.example.lib_kmp.navigation.core.AppNavKey

fun interface DeepLinkMapper {
    fun map(request: DeepLinkRequest): AppNavKey?
}
