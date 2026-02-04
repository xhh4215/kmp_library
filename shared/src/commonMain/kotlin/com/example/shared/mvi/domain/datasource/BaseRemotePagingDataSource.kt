package com.example.shared.mvi.domain.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shared.mvi.domain.data.DataResult


/**
 * @author 栾桂明
 * @param T  适配一次分页获取数据页面的pageSource
 */
abstract class BaseRemotePagingDataSource<DOMAIN : Any> : PagingSource<Int, DOMAIN>() {
    override fun getRefreshKey(state: PagingState<Int, DOMAIN>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DOMAIN> {
        val page = params.key ?: 1
        return try {
            when (val result = fetchPage(page, params.loadSize)) {
                is DataResult.Success -> {
                    val data = result.data
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                }

                is DataResult.Error -> LoadResult.Error(Exception(result.msg))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * 框架只知道分页页码和大小，具体请求业务层实现
     */
    protected abstract suspend fun fetchPage(page: Int, pageSize: Int): DataResult<List<DOMAIN>>
}




