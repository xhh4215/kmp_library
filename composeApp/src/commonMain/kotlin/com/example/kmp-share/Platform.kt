package com.example.`kmp-share`

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform