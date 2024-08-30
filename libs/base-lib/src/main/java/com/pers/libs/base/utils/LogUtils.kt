package com.pers.libs.base.utils

import android.util.Log
import com.pers.libs.base.BuildConfig

object LogUtils {

    private const val TAG = "AAA"

    fun e(vararg strings: Any?) {
        if (BuildConfig.DEBUG) {
            if (strings.isEmpty()) {
                return
            }
            val string = strings.joinToString(" ")
            Log.e(TAG, string)
        }
    }

    fun i(vararg strings: String?) {
        if (BuildConfig.DEBUG) {
            if (strings.isEmpty()) {
                return
            }
            val string = strings.joinToString(" ")
            Log.i(TAG, string)
        }
    }

    fun w(vararg strings: String?) {
        if (BuildConfig.DEBUG) {
            if (strings.isEmpty()) {
                return
            }
            val string = strings.joinToString(" ")
            Log.w(TAG, string)
        }
    }
}