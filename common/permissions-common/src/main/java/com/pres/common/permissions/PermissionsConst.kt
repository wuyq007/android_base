package com.pres.common.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.hjq.permissions.Permission

object PermissionsConst {


    /**
     * 定位权限
     */
    val LOCATION = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)


    /**
     * 后台定位
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    val BACKGROUND_LOCATION = LOCATION.plus(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    /**
     * 读写权限
     */
    val EXTERNAL_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    /**
     * 拍照、录制视频
     */
    val CAMERA = arrayOf(Manifest.permission.CAMERA)


    /**
     * 录制音频
     */
    val RECORD_AUDIO = arrayOf(Manifest.permission.RECORD_AUDIO)

    /**
     * 通知栏权限
     */
    val NOTIFICATION_SERVICE = arrayOf(Permission.NOTIFICATION_SERVICE)


    /**
     * 发送通知权限（Android 13.0 新增的权限） 为了兼容 Android 13 以下版本，框架会自动申请
     */
    val POST_NOTIFICATIONS = arrayOf(Permission.POST_NOTIFICATIONS)

    /**
     * 读取通讯录权限
     */
    val CONTACT = arrayOf(Permission.READ_CONTACTS)
}