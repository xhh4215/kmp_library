package com.example.shared.network.ktor

import com.example.shared.network.configure.NetworkConfig
import io.ktor.client.HttpClient

internal object KtorClientFactory {
    fun create(config: NetworkConfig): HttpClient = createPlatformClient(config)

}

internal expect fun createPlatformClient(config: NetworkConfig): HttpClient


