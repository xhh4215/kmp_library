package com.luanxh.shared.mvi.network.configure

data class NetworkConfig(
    val baseUrlProvider:()->String,
    val tokenProvider: TokenProvider,
    val enableLog: Boolean = false
)