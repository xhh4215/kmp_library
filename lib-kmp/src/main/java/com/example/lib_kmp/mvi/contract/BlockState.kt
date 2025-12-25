package com.example.lib_kmp.mvi.contract

sealed interface BlockState<out T>: MviState {
    object Idle : BlockState<Nothing>
    object Loading : BlockState<Nothing>
    data class Success<T>(val data: T) : BlockState<T>
    data class Error(val message: String) : BlockState<Nothing>
}
