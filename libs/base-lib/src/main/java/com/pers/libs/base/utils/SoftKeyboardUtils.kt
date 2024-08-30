package com.pers.libs.base.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.pers.libs.base.app.AppConfig

object SoftKeyboardUtils {


    @JvmStatic
    fun showKeyboard(editText: View) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val imm = AppConfig.application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }


    @JvmStatic
    fun hideKeyboard(editText: View) {
        editText.isFocusable = false
        val imm = AppConfig.application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 软键盘是否显示
     */
    @JvmStatic
    fun hasSoftInput(fragment: Fragment): Boolean {
        return hasSoftInput(fragment.requireActivity())
    }

    @JvmStatic
    fun hasSoftInput(activity: Activity): Boolean {
        return ViewCompat.getRootWindowInsets(activity.window.decorView)?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
    }

    /**
     * 当前软键盘显示高度
     */
    @JvmStatic
    fun getSoftInputHeight(fragment: Fragment): Int {
        return getSoftInputHeight(fragment.requireActivity())
    }

    @JvmStatic
    fun getSoftInputHeight(activity: Activity): Int {
        val softInputHeight = ViewCompat.getRootWindowInsets(activity.window.decorView)?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
        return softInputHeight ?: 0
    }

}