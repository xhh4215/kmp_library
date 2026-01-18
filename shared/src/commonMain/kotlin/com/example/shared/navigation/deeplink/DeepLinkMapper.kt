package com.example.shared.navigation.deeplink

import com.example.shared.navigation.core.AppNavKey


fun interface DeepLinkMapper {
    fun map(request: DeepLinkRequest): AppNavKey?
}
