package com.example.shared.mvi.network.ktor

import com.example.shared.mvi.network.configure.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createPlatformClient(config: NetworkConfig): HttpClient = HttpClient(Darwin) {
    commonConfig(config)
}