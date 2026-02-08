package com.luanxh.shared.network.ktor

import com.luanxh.shared.mvi.network.configure.NetworkConfig
import com.luanxh.shared.mvi.network.ktor.commonConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun createPlatformClient(config: NetworkConfig): HttpClient {
   return  HttpClient(OkHttp) {
        commonConfig(
            config = config
        )
    }
}

