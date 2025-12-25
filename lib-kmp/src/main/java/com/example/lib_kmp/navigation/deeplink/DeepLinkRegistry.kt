package com.luanxh.navigation.deeplink

import com.example.lib_kmp.navigation.core.AppNavKey

class DeepLinkRegistry {

    private val mappers = mutableListOf<DeepLinkMapper>()

    fun register(mapper: DeepLinkMapper) {
        mappers += mapper
    }

    fun resolve(request: DeepLinkRequest): AppNavKey? {
        return mappers.firstNotNullOfOrNull { it.map(request) }
    }
}
