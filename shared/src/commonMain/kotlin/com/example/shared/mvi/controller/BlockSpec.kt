package com.example.shared.mvi.controller

import com.example.shared.domain.data.DataResult

/***
 * 数据块描述
 */
data class BlockSpec<T>(
    val loader: suspend () -> DataResult<T>,
    val errorMsg: String,
    val autoLoad: Boolean = true
)