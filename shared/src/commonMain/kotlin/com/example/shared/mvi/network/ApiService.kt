package com.example.shared.mvi.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiService(
    val client: HttpClient
) {
    suspend inline fun <reified T> get(path: String): ApiResponse<T> {
        return client.get(path).body()
    }

    suspend inline fun <T> post(
        path: String,
        body: Any? = null
    ): ApiResponse<T> {
        return client.post(path) {
            setBody(body)
        }.body()
    }
}