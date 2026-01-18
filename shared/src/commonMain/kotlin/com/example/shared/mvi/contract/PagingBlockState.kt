package com.example.shared.mvi.contract

import androidx.paging.PagingData

sealed interface PagingBlockState<out UI> {
    object Idle : PagingBlockState<Nothing>
    object Loading : PagingBlockState<Nothing>
    data class Success<UI : Any>(val data: PagingData<UI>) : PagingBlockState<UI>
    data class Error(val message: String) : PagingBlockState<Nothing>
}