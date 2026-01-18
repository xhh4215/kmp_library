package com.example.shared.navigation.core


data class NavResultEvent<T : Any>(
    val id: String = UUID.randomUUID().toString(),
    val payload: T
)
