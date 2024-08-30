package com.pers.libs.base.ktx

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.pers.libs.base.app.AppConfig

/**
 * 防止多次点击的扩展函数
 *
 * @param interval 点击间隔, 单位毫秒, 默认500毫秒
 * @param listener 点击事件
 */
fun View.setIntervalOnClickListener(
    interval: Int = 500, listener: View.OnClickListener?
) {
    setOnClickListener(IntervalClickListener(interval, listener))
}

fun View.setWidth(width: Int) {
    val params = this.layoutParams
    params.width = width
    this.layoutParams = params
}

fun View.setHeight(height: Int) {
    val params = this.layoutParams
    params.height = height
    this.layoutParams = params
}

fun View.setSize(width: Int, height: Int) {
    val params = this.layoutParams
    params.width = width
    params.height = height
    this.layoutParams = params
}

fun View.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(left, top, right, bottom)
    this.layoutParams = params
}

fun View.setMargins(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(margin, margin, margin, margin)
    this.layoutParams = params
}


fun View.setMarginHorizontal(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(margin, marginTop, margin, marginBottom)
    this.layoutParams = params
}

fun View.setMarginVertical(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, margin, marginRight, margin)
    this.layoutParams = params
}


fun View.setMarginTop(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, margin, marginRight, marginBottom)
    this.layoutParams = params
}

fun View.setMarginBottom(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, marginTop, marginRight, margin)
    this.layoutParams = params
}

fun View.setMarginLeft(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(margin, marginTop, marginRight, marginBottom)
    this.layoutParams = params
}


fun View.setMarginRight(margin: Int) {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, marginTop, margin, marginBottom)
    this.layoutParams = params
}

fun View.setMarginTopStatusBarHeight() {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, marginTop + AppConfig.statusBarHeight, marginRight, marginBottom)
    this.layoutParams = params
}

fun View.setMarginBottomNavigationHeight() {
    val params = this.layoutParams as? ViewGroup.MarginLayoutParams ?: return
    params.setMargins(marginLeft, marginTop, marginRight, marginBottom + AppConfig.navigationHeight)
    this.layoutParams = params
}

fun View.setPaddings(padding: Int) {
    this.setPadding(padding, padding, padding, padding)
}

fun View.setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    this.setPadding(left, top, right, bottom)
}

fun View.setPaddingHorizontal(padding: Int) {
    this.setPadding(padding, paddingTop, padding, paddingBottom)
}

fun View.setPaddingVertical(padding: Int) {
    this.setPadding(paddingLeft, padding, paddingRight, padding)
}


fun View.setPaddingTop(padding: Int) {
    this.setPadding(paddingLeft, padding, paddingRight, paddingBottom)
}


fun View.setPaddingBottom(padding: Int) {
    this.setPadding(paddingLeft, paddingTop, paddingRight, padding)
}


fun View.setPaddingLeft(padding: Int) {
    this.setPadding(padding, paddingTop, paddingRight, paddingBottom)
}


fun View.setPaddingRight(padding: Int) {
    this.setPadding(paddingLeft, paddingTop, padding, paddingBottom)
}


/**
 * 设置多缩增加顶部状态栏的高度
 */
fun View.setPaddingTopStatusBarHeight() {
    this.setPadding(paddingLeft, paddingTop + AppConfig.statusBarHeight, paddingRight, paddingBottom)
}

/**
 * 设置缩进增加底部导航栏的高度
 */
fun View.setPaddingBottomNavigationHeight() {
    this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + AppConfig.navigationHeight)
}


private open class IntervalClickListener(private val interval: Int, private val listener: View.OnClickListener?) : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(view: View?) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            listener?.onClick(view)
        }
    }
}