package com.example.shared.network.configure

data class NetworkConfig(
    val baseUrlProvider:()->String,
    val tokenProvider: TokenProvider,
    val enableLog: Boolean = false
)