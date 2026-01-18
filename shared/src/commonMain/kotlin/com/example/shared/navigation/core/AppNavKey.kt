package com.example.shared.navigation.core

import androidx.navigation3.runtime.NavKey

/**
 * 项目中所有页面的“唯一标识”
 *
 * navigation3 的核心思想：
 * 👉 backStack 中只存 NavKey
 */
interface AppNavKey: NavKey

/**
 * Navigation3 contentKey 的默认实现
 *
 * 规则：
 * 1. 同一个 Route 类 → 不同参数 → 不同页面实例
 * 2. data class 自动基于 equals / hashCode
 */
fun defaultContentKey(key: AppNavKey): Any = key






