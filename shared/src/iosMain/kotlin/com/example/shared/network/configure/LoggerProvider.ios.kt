package com.example.shared.network.configure

import io.ktor.client.plugins.logging.Logger

import platform.Foundation.NSLog

actual fun platformLogger(): Logger =
    object : Logger {
        override fun log(message: String) {
            NSLog("Ktor: %s", message)
        }
    }