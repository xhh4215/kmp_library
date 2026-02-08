package com.example.shared.mvi.contract



sealed interface PagingState {
    object Idle : PagingState
    object Loading : PagingState
    object Empty : PagingState
    data class Error(val message: String) : PagingState
    object Success : PagingState
}
