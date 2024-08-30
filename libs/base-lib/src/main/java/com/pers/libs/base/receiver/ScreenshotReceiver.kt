package com.pers.libs.base.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * 监听屏幕截图，拍照
 */
class ScreenshotReceiver(private val onScreenshot: (Context, File) -> Unit) : BroadcastReceiver() {


    companion object {
        fun register(
            activity: Activity, onScreenshot: (context: Context, file: File) -> Unit = { _: Context, _: File -> }
        ): ScreenshotReceiver {
            val screenshotReceiver = ScreenshotReceiver(onScreenshot)
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_USER_PRESENT)
            filter.addAction(MediaStore.ACTION_IMAGE_CAPTURE)
            activity.registerReceiver(screenshotReceiver, filter)
            return screenshotReceiver
        }

        fun unregister(activity: Activity, screenshotReceiver: ScreenshotReceiver) {
            activity.unregisterReceiver(screenshotReceiver)
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_USER_PRESENT)) {
            // 用户解锁设备后检查是否有新截图
            if (context != null) {
                checkForScreenshots(context)
            }
        } else if (intent?.action.equals(MediaStore.ACTION_IMAGE_CAPTURE)) {
            // 用户拍照或截图
            if (context != null) {
                checkForScreenshots(context)
            }
        }
    }


    /**
     *  检查是否有新的截图
     */
    private fun checkForScreenshots(context: Context) {
        // 获取截图文件夹路径
        val screenshotsPath: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/Screenshots/"
        val screenshotsDir = File(screenshotsPath)
        if (screenshotsDir.exists() && screenshotsDir.isDirectory) {
            // 获取截图文件夹中的所有文件
            val files = screenshotsDir.listFiles()
            if (files != null && files.isNotEmpty()) {
                // 检查最近的文件是否为新截图
                val latestFile = files[files.size - 1]
                val lastModified = latestFile.lastModified()
                // 比较文件的最后修改时间与当前时间，如果很接近则认为是新截图
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastModified < 10000) { // 10 秒内
                    showScreenshotNotification(context, latestFile)
                }
            }
        }
    }

    private fun showScreenshotNotification(context: Context, file: File) {
        // 显示提示通知
//        ToastUtils.showToast("检测到最新图片:", file.absolutePath)
        onScreenshot.invoke(context, file)
    }


}


