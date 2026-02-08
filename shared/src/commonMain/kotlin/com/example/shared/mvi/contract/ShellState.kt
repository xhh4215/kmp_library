package com.example.shared.mvi.contract

sealed interface ShellState : MviState {
    object Idle : ShellState
    object Checking : ShellState        // 页面进入前校验
    object Ready : ShellState           // 页面可展示
    data class Error(val msg: String) : ShellState
}