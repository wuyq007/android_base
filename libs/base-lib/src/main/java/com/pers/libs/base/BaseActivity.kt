package com.pers.libs.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.AppLifecycleObserver
import com.pers.libs.base.app.removeAppLifecycleObserver
import com.pers.libs.base.databinding.ActivityBaseBinding
import com.pers.libs.base.utils.ScreenUtils
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: AppCompatActivity

    private lateinit var baseBinding: ActivityBaseBinding

    private lateinit var mainJob: CompletableJob
    override val coroutineContext: CoroutineContext
        get() = mainJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(baseBinding.root)

        mContext = AppConfig.application.applicationContext
        mActivity = this

        // 创建 Job （用于管理CoroutineScope中的所有携程）
        mainJob = SupervisorJob()

        initViews()
    }

    private fun initViews() {
        baseBinding.baseTitleLeftImage.setOnClickListener { finish() }
        baseBinding.baseTitleRightImage.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        // 当 Activity 销毁的时候取消该 Scope 管理的 job。 这样该 Scope 内创建的子 Coroutine 都会被自动的取消。
        mainJob.cancel()
        appLifecycleObserver?.let {
            removeAppLifecycleObserver(it)
        }
    }

    private var appLifecycleObserver: AppLifecycleObserver? = null



    open fun hideTitleLayout() {
        baseBinding.baseTitleLayout.visibility = View.GONE
    }

    protected open fun getTitleView(): AppCompatTextView {
        return baseBinding.baseTitleView
    }

    /**
     * 设置标题名称
     */
    protected open fun setTitleText(titleStr: String) {
        baseBinding.baseTitleView.text = titleStr
    }

    /**
     * 左边图标
     */
    protected open fun getTitleLeftImage(): AppCompatImageView {
        return baseBinding.baseTitleLeftImage
    }

    /**
     * 标题左边文字
     */
    protected open fun getTitleLeftText(): AppCompatTextView {
        return baseBinding.baseTitleLeftText
    }

    /**
     * 设置标题文字文字
     */
    protected open fun setTitleLeftText(text: String) {
        baseBinding.baseTitleLeftText.text = text
    }

    /**
     * 标题右边文字
     */
    protected open fun getTitleRightText(): AppCompatTextView {
        return baseBinding.baseTitleRightText
    }

    /**
     * 设置标题右边文字
     */
    protected open fun setTitleRightText(text: String) {
        baseBinding.baseTitleRightText.text = text
    }


    /**
     * 右边图标
     */
    protected open fun getTitleRightImage(): AppCompatImageView {
        return baseBinding.baseTitleRightImage
    }


    /**
     * 隐藏默认标题栏下的分割线
     */
    protected open fun hindTitleDivider(isHind: Boolean = false) {
        if (isHind) {
            baseBinding.baseTitleLayout.cardElevation = 0f
        } else {
            baseBinding.baseTitleLayout.cardElevation = 0f
        }
    }

    /**
     * 设置内容区域，如果不需要 BaseActivity 的默认布局，直接设置 setContentView 即可
     */
    protected open fun setContentLayout(@LayoutRes layoutResId: Int) {
        LayoutInflater.from(this).inflate(layoutResId, baseBinding.baseContentLayout, true)
    }

    protected open fun setContentLayout(view: View) {
        baseBinding.baseContentLayout.addView(view)
    }


    /**
     * 状态栏图标深色
     */
    open fun setStartBarModule(isDark: Boolean) {
        ScreenUtils.setStartBarModule(this, isDark)
    }

    /**
     * 状态栏深（非沉浸式状态栏才生效）
     */
    open fun setStartBarBackgroundColor(@ColorInt colorId: Int) {
        ScreenUtils.setStartBarBackgroundColor(this, colorId)
    }

    open fun setStartBarBackgroundColo(@Size(min = 1) colorString: String) {
        ScreenUtils.setStartBarBackgroundColoString(this, colorString)
    }

    open fun setStartBarBackgroundColorResource(@ColorRes colorId: Int) {
        ScreenUtils.setStartBarBackgroundColorResource(this, colorId)
    }


    /**
     * 开启沉浸式状态栏
     */
    open fun enableStartBarImmersive() {
        ScreenUtils.enableStartBarImmersive(this)
        setFitsSystemWindows(baseBinding.baseTitleLayout)
    }

    /**
     * 沉浸式预留状态栏高度,
     * @param view 给 这个View 加上一个状态栏高度的 paddingTop
     */
    open fun setFitsSystemWindows(
        view: View = findViewById<ViewGroup>(android.R.id.content).getChildAt(
            0
        )
    ) {
        view.fitsSystemWindows = true
    }

}