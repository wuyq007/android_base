package com.pers.libs.base.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.pers.libs.base.app.AppConfig

fun AppCompatActivity.dp2px(dp: Float): Float {
    return ScreenUtils.dp2px(dp)
}

fun AppCompatActivity.dp2px(dp: Int): Int {
    return ScreenUtils.dp2px(dp)
}

fun AppCompatActivity.px2dp(dp: Float): Float {
    return ScreenUtils.px2dp(dp)
}

fun AppCompatActivity.px2dp(dp: Int): Int {
    return ScreenUtils.px2dp(dp)
}

fun AppCompatActivity.sp2px(dp: Float): Float {
    return ScreenUtils.sp2px(dp)
}

fun AppCompatActivity.sp2px(dp: Int): Int {
    return ScreenUtils.sp2px(dp)
}

fun AppCompatActivity.px2sp(dp: Float): Float {
    return ScreenUtils.px2sp(dp)
}

fun AppCompatActivity.px2sp(dp: Int): Int {
    return ScreenUtils.px2sp(dp)
}

fun Fragment.dp2px(dp: Float): Float {
    return ScreenUtils.dp2px(dp)
}

fun Fragment.dp2px(dp: Int): Int {
    return ScreenUtils.dp2px(dp)
}

fun Fragment.px2dp(dp: Float): Float {
    return ScreenUtils.px2dp(dp)
}

fun Fragment.px2dp(dp: Int): Int {
    return ScreenUtils.px2dp(dp)
}

fun Fragment.sp2px(dp: Float): Float {
    return ScreenUtils.sp2px(dp)
}

fun Fragment.sp2px(dp: Int): Int {
    return ScreenUtils.sp2px(dp)
}

fun Fragment.px2sp(dp: Float): Float {
    return ScreenUtils.px2sp(dp)
}

fun Fragment.px2sp(dp: Int): Int {
    return ScreenUtils.px2sp(dp)
}


fun AppCompatActivity.enableStartBarImmersive() {
    ScreenUtils.enableStartBarImmersive(this)
}

fun AppCompatActivity.setStartBarModule(sDark: Boolean = false) {
    ScreenUtils.setStartBarModule(this, sDark)
}

fun AppCompatActivity.setStartBarBackgroundColorResource(@ColorRes colorId: Int) {
    ScreenUtils.setStartBarBackgroundColorResource(this, colorId)
}

fun AppCompatActivity.setStartBarBackgroundColoString(@Size(min = 1) colorString: String) {
    ScreenUtils.setStartBarBackgroundColoString(this, colorString)
}

fun AppCompatActivity.setStartBarBackgroundColor(@ColorInt colorInt: Int) {
    ScreenUtils.setStartBarBackgroundColor(this, colorInt)
}

fun AppCompatActivity.hideNavigationBar(isHide: Boolean = false) {
    ScreenUtils.hideNavigationBar(this, isHide)
}

fun AppCompatActivity.hideStartBar(isHide: Boolean = false) {
    ScreenUtils.hideStartBar(this, isHide)
}

fun Fragment.enableStartBarImmersive() {
    this.activity?.let { ScreenUtils.enableStartBarImmersive(it) }
}

fun Fragment.setStartBarModule(sDark: Boolean = false) {
    this.activity?.let { ScreenUtils.setStartBarModule(it, sDark) }
}

fun Fragment.setStartBarBackgroundColorResource(@ColorRes colorId: Int) {
    this.activity?.let { ScreenUtils.setStartBarBackgroundColorResource(it, colorId) }
}

fun Fragment.setStartBarBackgroundColoString(@Size(min = 1) colorString: String) {
    this.activity?.let { ScreenUtils.setStartBarBackgroundColoString(it, colorString) }
}

fun Fragment.setStartBarBackgroundColor(@ColorInt colorInt: Int) {
    this.activity?.let { ScreenUtils.setStartBarBackgroundColor(it, colorInt) }
}

fun Fragment.hideNavigationBar(isHide: Boolean = false) {
    this.activity?.let { ScreenUtils.hideNavigationBar(it, isHide) }
}

fun Fragment.hideStartBar(isHide: Boolean = false) {
    this.activity?.let { ScreenUtils.hideStartBar(it, isHide) }
}


object ScreenUtils {

    fun dp2px(dp: Float): Float {
        val density = AppConfig.application.resources.displayMetrics.density
        return dp * density
    }

    fun dp2px(dp: Int): Int {
        val density = AppConfig.application.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    fun px2dp(px: Float): Float {
        val density = AppConfig.application.resources.displayMetrics.density
        return px / density
    }

    fun px2dp(px: Int): Int {
        val density = AppConfig.application.resources.displayMetrics.density
        return (px / density).toInt()
    }

    fun sp2px(sp: Float): Float {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return sp * scaledDensity
    }

    fun sp2px(sp: Int): Int {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return (sp * scaledDensity).toInt()
    }

    fun px2sp(px: Float): Float {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return px / scaledDensity
    }

    fun px2sp(px: Int): Int {
        val metrics = AppConfig.application.resources.displayMetrics
        val scaledDensity = metrics.scaledDensity
        return (px / scaledDensity).toInt()
    }

    /**
     * 启用沉浸式状态栏
     */
    fun enableStartBarImmersive(activity: Activity) {
        val window: Window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = Color.TRANSPARENT;
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
            val decorView = window.decorView
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    /**
     * 设置状态栏深色模式
     *  @param isDark 是否深色模式
     */
    fun setStartBarModule(activity: Activity, isDark: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(activity.window.decorView)
            if (controller != null) {
                controller.isAppearanceLightStatusBars = isDark
                return
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                activity.window.decorView.setSystemUiVisibility(0)
            }
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    fun setStartBarBackgroundColorResource(activity: Activity, @ColorRes colorId: Int) {
        setStartBarBackgroundColor(activity, ContextCompat.getColor(activity.applicationContext, colorId))
    }

    fun setStartBarBackgroundColoString(activity: Activity, @Size(min = 1) colorString: String) {
        setStartBarBackgroundColor(activity, Color.parseColor(colorString))
    }

    fun setStartBarBackgroundColor(activity: Activity, @ColorInt colorInt: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = colorInt
        }
    }


    /**
     * 启用导航栏沉浸式
     */
    fun enableNavigationBarImmersive(activity: Activity, enable: Boolean) {
        val window: Window = activity.window
        if (enable) {
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            window.decorView.systemUiVisibility = uiOptions
            //将底部导航栏背景设置为透明
            setNavigationBarColor(activity, Color.TRANSPARENT)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 设置底部虚拟按键 \ 底部导航栏 图标颜色
     *
     * @param isDark = true  深色背景，白色图标
     * @param isDark = false  浅色背景，黑色图标
     */
    fun setNavigationBarModule(activity: Activity, isDark: Boolean = false) {
        if (Build.VERSION.SDK_INT >= 30) {
            val controller = ViewCompat.getWindowInsetsController(activity.window.decorView)
            if (controller != null) {
                controller.isAppearanceLightNavigationBars = isDark
                return
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                activity.window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            } else {
                activity.window.decorView.setSystemUiVisibility(0)
            }
        }
    }


    /**
     * 设置底部导航栏背景（小米手机如果设置了 setNavigationBarModule(false)，又同时设置了深色背景，导航栏的图标会改成浅色模式）
     */
    fun setNavigationBarColorResource(activity: Activity, @ColorRes colorId: Int) {
        setNavigationBarColor(
            activity, ContextCompat.getColor(activity.applicationContext, colorId)
        )
    }

    fun setNavigationBarColorString(activity: Activity, @Size(min = 1) colorString: String) {
        setNavigationBarColor(activity, Color.parseColor(colorString))
    }

    fun setNavigationBarColor(activity: Activity, @ColorInt colorInt: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.navigationBarColor = colorInt
        }
    }

    /**
     * 隐藏导航栏
     */
    fun hideNavigationBar(activity: Activity, isHide: Boolean = false) {
        val decorView: View = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(decorView)
            if (controller != null) {
                if (isHide) {
                    // 隐藏导航栏
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                } else {
                    // 显示导航栏
                    controller.show(WindowInsetsCompat.Type.navigationBars())
                }
                return
            }
        }
        if (isHide) {
            //隐藏导航栏
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            //显示导航栏
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    /**
     * 隐藏状态栏
     */
    fun hideStartBar(activity: Activity, isHide: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = ViewCompat.getWindowInsetsController(activity.window.decorView)
            if (controller != null) {
                if (isHide) {
                    // 隐藏状态栏
                    controller.hide(WindowInsetsCompat.Type.statusBars())
                } else {
                    // 显示状态栏
                    controller.show(WindowInsetsCompat.Type.statusBars())
                }
                return
            }
        }
        if (isHide) {
            //移除状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            //显示状态栏
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 禁止 屏幕截屏
     */
    fun disableScreenCapture(activity: Activity, isDisable: Boolean) {
        if (isDisable) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }


}