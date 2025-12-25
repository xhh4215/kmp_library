package com.example.kmp_library

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform