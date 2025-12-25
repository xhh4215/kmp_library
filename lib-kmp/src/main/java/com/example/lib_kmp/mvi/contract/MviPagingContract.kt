package com.example.lib_kmp.mvi.contract

import androidx.paging.compose.LazyPagingItems

object MviPagingContract {


     //使用Paging3 默认的分页逻辑
     data class Paging3ViewState<T : Any>(
         val pagingData: LazyPagingItems<T>? = null,
         val pageStatus: PageStatus = PageStatus.Idle,
     ) : MviState



     sealed class Paging3Effect : MviEffect {
         data class Toast(val msg: String) : Paging3Effect()
     }



 }