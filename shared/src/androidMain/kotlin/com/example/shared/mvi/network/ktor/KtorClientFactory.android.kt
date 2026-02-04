package com.example.shared.mvi.network.ktor

import com.example.shared.mvi.network.configure.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun createPlatformClient(config: NetworkConfig): HttpClient {
   return  HttpClient(OkHttp) {
        commonConfig(
            config = config
        )
    }
}

