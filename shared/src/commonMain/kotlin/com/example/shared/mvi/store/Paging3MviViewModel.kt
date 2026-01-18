package com.example.shared.mvi.store

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.shared.mvi.contract.MviIntent
import com.example.shared.mvi.contract.MviPagingContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest


abstract class Paging3MviViewModel<I : MviIntent, UI : Any, PARAM : Any> :
    BaseMviViewModel<I, MviPagingContract.Paging3ViewState<UI>, MviPagingContract.Paging3Effect>() {

    override fun initialState(): MviPagingContract.Paging3ViewState<UI> =
        MviPagingContract.Paging3ViewState()

    /** 子类只提供“参数流” */
    protected abstract val pagingParamFlow: StateFlow<PARAM>

    /** 子类只负责：param → PagingSource */
    protected abstract fun createPagingSource(param: PARAM): PagingSource<Int, UI>
    /*** Paging3 流
    //flatMapLastest
    //把上游元素映射成 新的流（Flow），并 只收集最近映射出来的那个内层流 —— 当上游发来新元素时，会取消当前正在收集的内层流，切换去收集新的内层流。
    //map { value -> transform(value) }
    //映射成单个值，不是 Flow。
    //
    //mapLatest { value -> ... }
    //mapLatest 把上游元素映射成单个结果（或 suspend 计算），如果上游来新值，会取消正在进行的映射任务（不是取消内层 Flow，因为没有内层 Flow）。
    //
    //flatMapConcat { value -> flowOf(...) }
    //顺序收集内层流：不会取消前一个内层流，按序完成（串行拼接）。
    //
    //flatMapMerge(concurrency = n) { value -> flowOf(...) }
    //并行收集多个内层流（最多并发 n 个），不会取消旧流，适合并行任务合并结果。
    //
    //flatMapLatest = switchMap（Rx）语义：只保留最新。
    //val queryFlow: StateFlow<String> = MutableStateFlow("")
    //
    //val results = queryFlow
    //    .debounce(300)                // 防抖
    //    .distinctUntilChanged()       // 去重复
    //    .flatMapLatest { q ->
    //        repository.search(q)      // 返回 Flow<List<Item>>
    //           .catch { emit(emptyList()) } // 捕获错误
    //    }
     **/
    /** Base 统一创建 Pager */
    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow: Flow<PagingData<UI>> by lazy {
        pagingParamFlow
            .flatMapLatest { param ->
                Pager(
                    config = PagingConfig(
                        pageSize = pageSize(param),
                        initialLoadSize = pageSize(param)
                    ),
                    pagingSourceFactory = {
                        requireNotNull(createPagingSource(param)) {
                            "createPagingSource() returned null!"
                        }
                    }
                ).flow
            }
            .cachedIn(viewModelScope)
    }


    /** 子类可覆盖 */
    protected open fun pageSize(param: PARAM): Int = 10
}










