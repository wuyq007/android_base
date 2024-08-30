package com.pers.libs.net

import com.pers.libs.base.app.AppConfig
import com.pers.libs.base.app.AppSystemInfo


object HttpParam {

    /**
     * Body的通用参数
     */
    suspend fun addBaseBodyParam(): HashMap<String, Any> {
        val bodyParams = HashMap<String, Any>()
        //配置body通用参数
        // ...

        return bodyParams
    }


    /**
     * Header的通用参数
     */
    suspend fun addBaseHeaderParam(): HashMap<String, Any> {
        val headerParams = HashMap<String, Any>()

        //os                      客户端           (Android / iOS)
        //deviceBrand     手机品牌       (huawei/xiaomi/iphone)
        //deviceModel     手机型号
        //deviceVersion   系统版本       (安卓是 10，11，12...   iOS 是 15.0  16.4 17.0 ...)
        //appVersion      应用版本号   (2.0.0)
        //channel         安装渠道号    (iOS 固定 appStore； 安卓是：baidu/huawei/xiaomi/oppo/vivo/yyb )（已编辑）

        headerParams["os"] = "Android"
        headerParams["deviceBrand"] = AppSystemInfo.deviceBrand
        headerParams["deviceModel"] = AppSystemInfo.systemModel
        headerParams["deviceVersion"] = AppSystemInfo.systemVersion
        headerParams["appVersion"] = AppConfig.versionName
        headerParams["channel"] = AppConfig.appChannel
        headerParams["deviceId"] = AppConfig.deviceId

//        LogUtils.e("headerParams", headerParams.toJson())

        //设置 token
//        headerParams["sso"] = UserConfig.token

        return headerParams
    }
}
