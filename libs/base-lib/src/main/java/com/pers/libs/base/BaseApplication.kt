package com.pers.libs.base

import android.content.Context
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.addAppLifecycleObserver
import com.pers.libs.base.app.AppLifecycleObserver
import com.pers.libs.base.utils.LogUtils

open class BaseApplication : MultiDexApplication() {

    @CallSuper
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
        enabledAppLifecycleObserver()
    }

    /**
     * 监听程序是否退出到后台
     */
    private fun enabledAppLifecycleObserver() {
        AppConfig.application.addAppLifecycleObserver {
            object : AppLifecycleObserver {
                override fun onAppStarted() {
                    onAppStart()
                }

                override fun onAppStopped() {
                    onAppStop()
                }
            }
        }
    }

    open fun onAppStart() {
        LogUtils.e("程序回到前台")
    }

    open fun onAppStop() {
        LogUtils.e("程序最小化到后台")
    }
}