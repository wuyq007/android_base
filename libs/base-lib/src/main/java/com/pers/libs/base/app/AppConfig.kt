package com.pers.libs.base.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.pers.libs.base.utils.DataStoreUtils
import com.pers.libs.base.utils.CodedUtils
import com.pers.libs.base.utils.ScreenUtils
import kotlinx.coroutines.*
import java.util.*

object AppConfig {

    private lateinit var app: Application

    @JvmStatic
    fun init(app: Application) {
        AppConfig.app = app
    }

    /**
     * 程序从后台返回前台时，更新一次数据
     */
    fun update() {
        //改变虚拟按键会导致屏幕高度改变，这里更新一次
        screenWidth = Holder.APP_SCREEN_WIDTH()
        screenHeight = Holder.APP_SCREEN_HEIGHT()
        navigationHeight = Holder.APP_NAVIGATION_BAR_HEIGHT()
    }

    /**
     * 全局 Context
     */
    @JvmStatic
    val application: Application by lazy { app }

    /**
     * 获取应用包名
     */
    @JvmStatic
    val packageName: String by lazy { app.packageName }

    /**
     * 获取应用名名称
     */
    @JvmStatic
    val appName: String by lazy { Holder.appName }

    /**
     * 获取应用版本名称
     */
    @JvmStatic
    val versionName: String by lazy { Holder.APP_VERSION_NAME }

    /**
     * 获取应用版本Code
     */
    @JvmStatic
    val versionCode: Long by lazy { Holder.APP_VERSION_CODE }


    @JvmStatic
    val targetSdkVersion: Int by lazy { Holder.TARGET_SDK_VERSION }

    /**
     * APP 渠道
     */
    @JvmStatic
    val appChannel: String by lazy { Holder.appChannel }

    /**
     * 获取状态栏高度
     */
    @JvmStatic
    val statusBarHeight: Int by lazy { Holder.APP_STATUS_BAR_HEIGHT }

    /**
     * 获取底部导航栏高度
     *
     * 小米12手机，启用手势后底部导航栏也回有一点高度，类似 iPhone X 的底部预留高度
     *
     * 华为荣耀、VIVO 手机上，不管虚拟按键是否开启，都会返回虚拟按键的高度
     */
    @JvmStatic
    var navigationHeight: Int = 0
        get() {
            if (Build.MANUFACTURER.equals("HUAWEI") && !isShowBottomNavigationBar) {
                return 0
            }
            if (Build.MANUFACTURER.equals("vivo") && !isShowBottomNavigationBar) {
                return 0
            }
            if (field == 0) {
                field = Holder.APP_NAVIGATION_BAR_HEIGHT()
            }
            return field
        }
        private set

    /**
     * 判断底部虚拟按键是否显示
     */
    @JvmStatic
    var isShowBottomNavigationBar: Boolean = false
        get() {
            field = Holder.IS_SHOW_BOTTOM_NAVIGATION_BAR()
            return field
        }

    /**
     * 屏幕的真实宽高
     */
    @JvmStatic
    val realWidth: Int by lazy { Holder.APP_REAL_WIDTH }

    @JvmStatic
    val realHeight: Int by lazy { Holder.APP_REAL_HEIGHT }

    @JvmStatic
    val windowWidth: Int by lazy {
        if (app.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            realWidth - statusBarHeight - navigationHeight
        } else {
            screenWidth
        }
    }

    @JvmStatic
    val windowHeight: Int by lazy {
        if (app.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            realHeight - statusBarHeight - navigationHeight
        } else {
            screenHeight
        }
    }

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    var screenWidth: Int = 0
        get() {
            if (field == 0) {
                field = Holder.APP_SCREEN_WIDTH()
            }
            return field
        }
        private set

    /**
     * 获取屏幕高度（屏幕可用高度，不包含状态栏、底部的导航栏、虚拟按键的高度）
     */
    @JvmStatic
    var screenHeight: Int = 0
        get() {
            if (field == 0) {
                field = Holder.APP_SCREEN_HEIGHT()
            }
            return field
        }
        private set

    /**
     * 判断当前是否是debug模式
     */
    @JvmStatic
    val isDebug: Boolean by lazy { Holder.APP_IS_DEBUG }

    /**
     * 判断当前是否是主线程
     */
    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread.id == currentThreadId()
    }

    @JvmStatic
    fun currentThreadName(): String {
        return Thread.currentThread().name
    }

    @JvmStatic
    fun currentThreadId(): Long {
        return Thread.currentThread().id
    }

    /**
     * 设备ID
     */
    @JvmStatic
    val deviceId: String by lazy { Holder.ANDROID_DEVICE_UNIQUE_ID }

    @JvmStatic
    val deviceVersion: Int by lazy { Build.VERSION.SDK_INT }


    @SuppressLint("InternalInsetResource")
    private object Holder {
        private val packageInfo: PackageInfo? by lazy {
            try {
                val pm: PackageManager = app.packageManager
                pm.getPackageInfo(packageName, 0)
            } catch (e: Exception) {
                null
            }
        }

        val applicationInfo: ApplicationInfo? by lazy {
            try {
                val appInfo = app.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
                appInfo
            } catch (e: Exception) {
                null
            }
        }

        val appName: String by lazy {
            if (applicationInfo != null) {
                val appName = app.packageManager.getApplicationLabel(applicationInfo!!).toString()
                appName
            } else {
                ""
            }
        }
        val appChannel: String by lazy {
            if (applicationInfo != null) {
                val appChannel = applicationInfo?.metaData?.getString("APP_CHANNEL_NAME")
                appChannel ?: ""
            } else {
                ""
            }
        }

        val APP_VERSION_NAME: String = packageInfo?.versionName ?: "1.0.0"
        val APP_VERSION_CODE: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo?.longVersionCode ?: 1L
        } else {
            (packageInfo?.versionCode)?.toLong() ?: 1L
        }
        val TARGET_SDK_VERSION: Int = packageInfo?.applicationInfo?.targetSdkVersion ?: Build.VERSION.SDK_INT


        /**
         * 华为上，不管虚拟按键是否开启，都会返回虚拟按键的高度
         */
        val APP_NAVIGATION_BAR_HEIGHT: () -> Int = {
            try {
                val resources: Resources = application.resources
                val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    resources.getDimensionPixelSize(resourceId)
                } else {
                    0
                }
            } catch (e: Exception) {
                0
            }
        }


        val IS_SHOW_BOTTOM_NAVIGATION_BAR: () -> Boolean = isShowBottomNavigationBar@{
            //否启用全屏手势滑动。
            var enabled: Boolean = true
            if (Build.MANUFACTURER.equals("Xiaomi")) {
                //判断小米手机是否启用了全屏手势
                enabled = Settings.Global.getInt(application.contentResolver, "force_fsg_nav_bar", 0) != 0
                return@isShowBottomNavigationBar !enabled
            } else if (Build.MANUFACTURER.equals("HUAWEI")) {
                //判断华为手机是否启用了全屏手势
                enabled = Settings.Global.getInt(application.contentResolver, "navigationbar_is_min", 0) != 0
                return@isShowBottomNavigationBar !enabled
            } else if (Build.MANUFACTURER.equals("vivo")) {
                // VIVO 手机
                enabled = Settings.Secure.getInt(application.contentResolver, "navigation_gesture_on", 0) != 0
                return@isShowBottomNavigationBar !enabled
            } else {
                if (navigationHeight == 0) {
                    //导航栏高度为0，判断虚拟按键未显示
                    return@isShowBottomNavigationBar false
                }
                val navigationHeightDp = ScreenUtils.px2dp(navigationHeight)
                // （一般手机的虚拟按键高度在 40dp+，隐藏虚拟按键时，有的手机会有手势的底部导航栏，高度一般在16dp）
                // 底部导航栏高度 小于20dp，判断虚拟按键是隐藏的
                enabled = navigationHeightDp < 20
            }
            !enabled
        }

        val APP_STATUS_BAR_HEIGHT: Int by lazy {
            try {
                val resources: Resources = app.resources
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    resources.getDimensionPixelSize(resourceId)
                } else {
                    0
                }
            } catch (e: Exception) {
                0
            }
        }

        private val realDisplayMetrics: DisplayMetrics? by lazy {
            val wm = app.getSystemService(Context.WINDOW_SERVICE)
            if (wm is WindowManager) {
                val display: Display = wm.defaultDisplay
                val metrics = DisplayMetrics()
                display.getRealMetrics(metrics)
                metrics
            } else {
                null
            }
        }

        val APP_REAL_WIDTH: Int = realDisplayMetrics?.widthPixels ?: 0
        val APP_REAL_HEIGHT: Int = realDisplayMetrics?.heightPixels ?: 0


        val displayMetrics: () -> DisplayMetrics? = {
            try {
                val resources: Resources = app.resources
                resources.displayMetrics
            } catch (e: Exception) {
                null
            }
        }

        val APP_SCREEN_WIDTH: () -> Int = {
            displayMetrics()?.widthPixels ?: 0
        }

        val APP_SCREEN_HEIGHT: () -> Int = {
            displayMetrics()?.heightPixels ?: 0
        }

        val APP_IS_DEBUG: Boolean by lazy {
            val ai: ApplicationInfo = app.applicationInfo
            ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }

        val ANDROID_DEVICE_UNIQUE_ID: String by lazy {
            //协程阻塞执行，同步返回
            runBlocking {
                var uniqueID: String? = DataStoreUtils.getString("ANDROID_DEVICE_UNIQUE_ID")
                if (uniqueID == null || uniqueID == "") {
                    //添加一个标识
                    val identification = packageName
                    val systemStr = try {
                        identification + AppSystemInfo.androidID + AppSystemInfo.systemModel + AppSystemInfo.board + AppSystemInfo.hardware + AppSystemInfo.deviceBrand + AppSystemInfo.deviceManufacturer + AppSystemInfo.fingerprint + AppSystemInfo.buildId + AppSystemInfo.serial
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ""
                    }
                    val uuid = UUID(
                        systemStr.hashCode().toLong(),
                        AppSystemInfo.serial.hashCode().toLong(),
                    )
                    uniqueID = CodedUtils.md5(systemStr, uuid.toString())
                    DataStoreUtils.saveString("ANDROID_DEVICE_UNIQUE_ID", uniqueID)
                }
                uniqueID
            }
        }
    }

}