package com.example.shared.navigation.runtime

import com.example.shared.navigation.core.AppNavKey


// commonMain

interface Navigator {

    /** 前进 */
    fun navigate(key: AppNavKey)

    /** 返回 */
    fun back()

    /** 清空并跳转 */
    fun replace(key: AppNavKey)

    fun <T : Any> setResult(result: T)


}


