package com.example.shared.network

import com.example.shared.network.configure.NetworkConfig
import com.example.shared.network.ktor.KtorClientFactory

object NetWork {
     private var initialized = false
     private lateinit var configure: NetworkConfig

     fun init(configure: NetworkConfig) {
         this.configure = configure
         initialized = true
     }

    internal fun requireConfigure(): NetworkConfig {
        check(initialized) { "NetworkConfig already initialized." }
        return configure
    }

    val api:ApiService by lazy {
        ApiService(KtorClientFactory.create(configure))
    }
 }