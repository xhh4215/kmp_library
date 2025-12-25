package com.example.lib_kmp.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson

object KtorClientFactory {
    private val clientMap = mutableMapOf<String, HttpClient>()

    fun create(baseUrl: String, token: String): HttpClient {
        return clientMap.getOrPut(baseUrl) {
            buildClient(baseUrl, token)
        }
    }

    private fun buildClient(baseUrl: String, token: String): HttpClient {
        return HttpClient(OkHttp) {
            install(DefaultRequest) {
                url(baseUrl)
                header("Content-Type", "application/json")

                // 全局添加 Bearer Token
                token?.let {
                    header("Authorization", "Bearer $it")
                }
            }

            install(ContentNegotiation) {
                gson {
                    setLenient()
                    disableHtmlEscaping()
                }
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }

            install(DefaultRequest) {
                url(baseUrl)
                header("Content-Type", "application/json")
            }


            HttpResponseValidator {
                validateResponse { response ->
                    val code = response.status.value
                    if (code !in 200..299) {
                        throw ResponseException(response, "HTTP $code Error")
                    }
                }
            }
        }
    }

    /** 可选：释放所有客户端 */
    fun clear() {
        clientMap.values.forEach { it.close() }
        clientMap.clear()
    }
}