package com.pers.libs.base.tools

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*

/**
 * 延时器
 */
class DelayTimer(
    //延迟 ms
    private val delayTime: Long = 0L,
    //回调
    private val callback: (() -> Unit)
) {
    private var job: Job? = null

    fun start() {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        job = coroutineScope.launch(Dispatchers.Main) {
            delay(delayTime)
            callback.invoke()
            cancel()
        }
    }

    fun cancel() {
        job?.cancel()
    }

    fun bindLifecycle(lifecycle: LifecycleOwner): DelayTimer {
        lifecycle.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                cancel()
            }
        })
        return this
    }

}