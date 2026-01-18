package com.example.shared.network.ktor

import com.example.shared.network.configure.NetworkConfig
import com.example.shared.network.configure.platformLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun createPlatformClient(networkConfig: NetworkConfig): HttpClient = HttpClient(OkHttp) {
    commonConfig(
        config = networkConfig
    )
}