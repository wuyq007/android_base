package com.pers.libs.base.tools

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn

/**
 * 可暂停的定时器
 */
class PausedTimer(
    //延迟 ms
    private val delayTime: Long = 0L,
    //间隔 ms
    private val intervalMillis: Long,
    //回调
    private val callback: ((Long) -> Unit)
) {

    constructor(intervalMillis: Long, callback: ((Long) -> Unit)) : this(
        0L, intervalMillis, callback
    )

    private var isPaused = false
    private var jop: Job

    init {
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        jop = coroutineScope.launch(Dispatchers.Main, CoroutineStart.LAZY) {
            if (delayTime > 0) {
                delay(delayTime)
            }
            flow {
                var counter = 0L
                while (true) {
                    if (!isPaused) {
                        emit(counter)
                        counter++
                    }
                    delay(intervalMillis)
                }
            }.flowOn(Dispatchers.IO).collect {
                callback.invoke(it)
            }
        }
    }

    fun start() {
        jop.start()
    }

    fun pause() {
        isPaused = true
    }

    fun resume() {
        isPaused = false
    }

    fun cancel() {
        jop.cancel()
    }

    fun bindLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                resume()
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                pause()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                cancel()
            }
        })
    }

    /**
     * 测试代码
     */
    private fun mainTest() {
        runBlocking() {
            //每 ${intervalMillis}毫秒执行一次
            val intervalMillis = 1000L
            val timer = PausedTimer(intervalMillis) {

            }

            //启动
            timer.start()

            //3s后暂停
            delay(3000)
            timer.pause()

            //2s后恢复
            delay(2000)
            timer.resume()

            //2s后取消
            delay(2000)
            timer.cancel()
        }
    }

}