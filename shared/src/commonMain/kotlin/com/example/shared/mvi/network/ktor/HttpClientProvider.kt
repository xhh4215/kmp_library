package com.example.shared.mvi.network.ktor

import com.example.shared.mvi.network.configure.NetworkConfig
import com.example.shared.mvi.network.configure.platformLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

fun HttpClientConfig<*>.commonConfig(
    config: NetworkConfig
) {
    //默认请求配置
    install(DefaultRequest) {
        url {
            takeFrom(config.baseUrlProvider())
            encodedPath  = encodedPath
        }
        header("Content-Type", "application/json")

    }

    //Auth 插件（自动刷新 Token 可扩展）
    install(Auth) {
        bearer {

            loadTokens {
                config.tokenProvider.getAccessToken()?.let { token ->
                    BearerTokens(
                        accessToken = token,
                        refreshToken = ""
                    )
                }
            }

            refreshTokens {
                config.tokenProvider.refreshAccessToken()?.let { newToken ->
                    BearerTokens(
                        accessToken = newToken,
                        refreshToken = ""
                    )
                }
            }
        }
    }
    if (config.enableLog) {
        install(Logging) {
            this.logger = platformLogger()
            level = LogLevel.ALL
        }

    }

    install(HttpTimeout) {

    }
}