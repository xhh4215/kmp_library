package com.launxh.shared.kmp

import android.os.Build
import com.example.`kmp-share`.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()