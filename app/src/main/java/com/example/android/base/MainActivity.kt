package com.example.android.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.android.base.databinding.ActivityMainBinding
import com.example.android.base.net.WanAndroidApiActivity
import com.pers.libs.base.BaseActivity
import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.AppSystemInfo.buildId
import com.pers.libs.base.app.AppSystemInfo.systemVersion
import com.pers.libs.base.utils.hideNavigationBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentLayout(binding.root)
        setStartBarModule(true)

        setContent()

        hideNavigationBar()

        launch {
            launch(Dispatchers.IO) {
                fetchData()
                Log.e("AAA", "launch 1")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 2")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 3")
            }
            launch(Dispatchers.IO) {
                Log.e("AAA", "launch 4")
            }

            Log.e("AAA", "launch 5")
        }

        binding.tvContent.setOnClickListener {
            startActivity(Intent(this, WanAndroidApiActivity::class.java))
        }

    }

    private suspend inline fun fetchData(): String {
        delay(3000L)
        Log.e("AAA", "fetchData")
        return "fetchData"
    }

    private fun setContent() {
        val stringBuilder = StringBuilder()

        AppConfig.apply {
            stringBuilder.append("appName：").append(appName).append("\n")
            stringBuilder.append("packageName：").append(packageName).append("\n")
            stringBuilder.append("versionName：").append(versionName).append("\n")
            stringBuilder.append("versionCode：").append(versionCode).append("\n")
            stringBuilder.append("deviceVersion：").append(deviceVersion).append("\n")
            stringBuilder.append("systemVersion：").append(systemVersion).append("\n")
            stringBuilder.append("buildId：").append(buildId).append("\n")
            stringBuilder.append("screenWidth：").append(screenWidth).append("\n")
            stringBuilder.append("screenHeight：").append(screenHeight).append("\n")
            stringBuilder.append("realWidth：").append(realWidth).append("\n")
            stringBuilder.append("realHeight：").append(realHeight).append("\n")
            stringBuilder.append("windowWidth：").append(windowWidth).append("\n")
            stringBuilder.append("windowHeight：").append(windowHeight).append("\n")
            stringBuilder.append("statusBarHeight：").append(statusBarHeight).append("\n")
            stringBuilder.append("navigationHeight：").append(navigationHeight).append("\n")
            stringBuilder.append("是否显示虚拟按键：").append(isShowBottomNavigationBar).append("\n")
            binding.tvContent.text = stringBuilder.toString()
        }

    }

}