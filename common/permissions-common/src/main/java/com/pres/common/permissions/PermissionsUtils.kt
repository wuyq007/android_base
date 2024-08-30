package com.pres.common.permissions

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.pers.libs.base.app.AppConfig

object PermissionsUtils {

    /**
     * 获取权限
     */
    fun getPermission(fragment: Fragment, permissions: Array<String>, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getPermission(it, permissions, listener)
        }
    }

    fun getPermission(activity: Activity, permissions: Array<String>, listener: PermissionsListener? = null) {
        XXPermissions.with(activity).permission(permissions).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (!allGranted) {
                    // 获取部分权限成功
                    listener?.onDenied(permissions)
                    return
                }
                // 获取权限成功
                listener?.onGranted(permissions)
            }

            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    //权限永久拒绝
                    listener?.onNeverAgain(permissions)
                } else {
                    //获取权限失败
                    listener?.onDenied(permissions)
                }
            }
        })
    }

    fun getPermission(context: Context, permissions: Array<String>, listener: PermissionsListener? = null) {
        XXPermissions.with(context).permission(permissions).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (!allGranted) {
                    // 获取部分权限成功
                    listener?.onDenied(permissions)
                    return
                }
                // 获取权限成功
                listener?.onGranted(permissions)
            }

            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    //权限永久拒绝
                    listener?.onNeverAgain(permissions)
                } else {
                    //获取权限失败
                    listener?.onDenied(permissions)
                }
            }
        })
    }


    /** 常用的一些权限获取 */
    /** 常用的一些权限获取 */
    /**
     * 获取存储权限\读写权限\相册权限
     */
    fun getStoragePermission(fragment: Fragment, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getStoragePermission(it, listener)
        }
    }

    fun getStoragePermission(activity: Activity, listener: PermissionsListener? = null) {
        getPermission(activity, PermissionsConst.EXTERNAL_STORAGE, listener)
    }


    /**
     * 获取定位权限
     */
    fun getLocationPermission(fragment: Fragment, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getLocationPermission(it, listener)
        }
    }

    fun getLocationPermission(activity: Activity, listener: PermissionsListener? = null) {
        getPermission(activity, PermissionsConst.LOCATION, listener)
    }

    fun getLocationPermission(context: Context, listener: PermissionsListener? = null) {
        getPermission(context, PermissionsConst.LOCATION, listener)
    }


    /**
     * 获取拍照、录制视频权限
     */
    fun getCameraPermission(fragment: Fragment, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getCameraPermission(it, listener)
        }
    }

    fun getCameraPermission(activity: Activity, listener: PermissionsListener? = null) {
        getPermission(activity, PermissionsConst.CAMERA, listener)
    }


    /**
     * 获取录音权限
     */
    fun getRecordAudioPermission(fragment: Fragment, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getRecordAudioPermission(it, listener)
        }
    }

    fun getRecordAudioPermission(activity: Activity, listener: PermissionsListener? = null) {
        getPermission(activity, PermissionsConst.RECORD_AUDIO, listener)
    }


    /**
     * 获取通知权限
     */
    fun getNotificationPermission(fragment: Fragment, listener: PermissionsListener? = null) {
        fragment.activity?.let {
            getNotificationPermission(it, listener)
        }
    }

    fun getNotificationPermission(activity: Activity, listener: PermissionsListener? = null) {
        val permission = if (AppConfig.targetSdkVersion < 33) {
            PermissionsConst.NOTIFICATION_SERVICE
        } else {
            PermissionsConst.POST_NOTIFICATIONS
        }
        getPermission(activity, permission, listener)
    }

    /**
     * 去通知栏设置页面
     */
    fun startNotificationPermissionActivity(activity: Activity) {
        XXPermissions.startPermissionActivity(activity, PermissionsConst.NOTIFICATION_SERVICE)
    }


    /**
     * 判断是否有权限
     */
    fun isPermissions(permission: String): Boolean {
        return XXPermissions.isGranted(AppConfig.application, permission)
    }

    fun isPermissions(permissions: Array<String>): Boolean {
        return XXPermissions.isGranted(AppConfig.application, permissions)
    }


    /**
     *  跳转权限设置页面
     */
    fun startPermissionActivity(activity: Activity, permission: String? = null) {
        if (permission != null) {
            XXPermissions.startPermissionActivity(activity, permission)
        } else {
            XXPermissions.startPermissionActivity(activity)
        }
    }

    fun startPermissionActivity(activity: Activity, permissions: List<String>) {
        XXPermissions.startPermissionActivity(activity, permissions)
    }

//    fun startPermissionActivity(fragment: Fragment, permissions: List<String>) {
//        XXPermissions.startPermissionActivity(fragment, permissions)
//    }

    fun startPermissionActivity(context: Context, permissions: List<String>) {
        XXPermissions.startPermissionActivity(context, permissions)
    }
}