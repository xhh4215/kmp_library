package com.example.shared.network.ktor

import com.example.shared.network.configure.NetworkConfig
import com.example.shared.network.configure.platformLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import platform.darwin.token_t

actual fun createPlatformClient(config: NetworkConfig): HttpClient = HttpClient(Darwin) {
    commonConfig(config)
}