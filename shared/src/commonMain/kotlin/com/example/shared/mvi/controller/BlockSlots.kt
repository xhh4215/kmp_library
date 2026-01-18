package com.example.shared.mvi.controller

import androidx.compose.runtime.Composable

data class BlockSlots<T>(
    val idle: @Composable () -> Unit = {},
    val loading: @Composable () -> Unit = {},
    val empty: @Composable () -> Unit = {},
    val error: @Composable (message: String, retry: () -> Unit) -> Unit = { _, _ -> },
    val content: @Composable (T) -> Unit
)