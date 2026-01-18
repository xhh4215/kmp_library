package com.example.shared.network.configure

// commonMain
interface TokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun refreshAccessToken(): String?
}