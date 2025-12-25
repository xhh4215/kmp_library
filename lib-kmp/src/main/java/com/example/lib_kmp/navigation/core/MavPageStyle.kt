package com.example.lib_kmp.navigation.core
/**
 * 页面展示方式
 *
 * navigation3 不关心这些
 * 👉 由 entryProvider 决定如何显示
 */
sealed interface MavPageStyle {
    data object Screen : MavPageStyle          // 普通页面
    data object Dialog : MavPageStyle           // 对话框
    data object BottomSheet : MavPageStyle      // 底部弹窗
}
