package com.luanxh.shared.mvi.network.configure

// commonMain
interface TokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun refreshAccessToken(): String?
}