package com.example.shared.network.configure

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger

actual fun platformLogger(): Logger {
   return  Logger.DEFAULT
}