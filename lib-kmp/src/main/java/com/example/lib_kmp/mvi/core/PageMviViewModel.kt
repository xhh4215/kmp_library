package com.luanxh.mvi.core.viewmodel

import com.example.lib_kmp.mvi.core.BaseMviViewModel
import com.luanxh.mvi.core.data.ListData
import com.luanxh.mvi.core.contract.MviContract
import com.luanxh.mvi.core.contract.MviIntent
import com.luanxh.mvi.core.data.PageData
import com.luanxh.mvi.core.contract.PageStatus
import com.luanxh.mvi.core.data.SingleData
import com.luanxh.mvi.core.data.DataResult
import com.luanxh.mvi.core.mapper.UiMapper


/***
 * @author 栾桂明
 * @date 2025年12月2日
 * @desc  对页面中的使用的ViewModel 在BaseMviViewModel 基础上再次封装
 */
abstract class PageMviViewModel<I : MviIntent, DOMAIN, UI> :
    BaseMviViewModel<I, MviContract.ViewState<UI>, MviContract.Effect>() {

    //默认页面的状态都是idle状态
    override fun initialState() = MviContract.ViewState<UI>(PageStatus.Idle)


    protected abstract val uiMapper: UiMapper<DOMAIN, UI>

    protected fun setLoading() {
        setState { copy(pageStatus = PageStatus.Loading) }
    }

    protected fun setEmpty() {
        setState { copy(pageStatus = PageStatus.Empty, data = null) }
    }

    protected fun setSuccess(data: PageData<UI>) {
        setState { copy(pageStatus = PageStatus.Success, data = data) }
    }

    protected fun setError(msg: String) {
        setState { copy(pageStatus = PageStatus.Error(msg)) }

    }

    protected abstract override fun handleIntent(intent: I)


    protected fun handleDataResult(result: DataResult<DOMAIN>) {
        when (result) {
            is DataResult.Success ->
                setSuccess(SingleData(uiMapper.mapToUi(result.data)))

            is DataResult.Error ->
                setError(result.msg)
        }
    }

    // 处理列表数据
    protected fun handleDataResultList(result: DataResult<List<DOMAIN>>) {
        when (result) {
            is DataResult.Success -> {
                val uiList = result.data.map { uiMapper.mapToUi(it) }
                if (uiList.isEmpty()) setEmpty()
                else setSuccess(ListData(uiList))
            }

            is DataResult.Error -> setError(result.msg)
        }
    }

}




