package com.example.lib_kmp.navigation.core

import java.util.UUID

data class NavResultEvent<T : Any>(
    val id: String = UUID.randomUUID().toString(),
    val payload: T
)
