package com.example.shared.navigation.deeplink

import com.example.shared.navigation.core.AppNavKey

class DeepLinkRegistry {

    private val mappers = mutableListOf<DeepLinkMapper>()

    fun register(mapper: DeepLinkMapper) {
        mappers += mapper
    }

    fun resolve(request: DeepLinkRequest): AppNavKey? {
        return mappers.firstNotNullOfOrNull { it.map(request) }
    }
}
