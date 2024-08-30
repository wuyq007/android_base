package com.pers.libs.base.utils

import com.pers.libs.base.app.AppSystemInfo

object RomUtils {


    private val ROM_HUAWEI = arrayOf("huawei")
    private val ROM_VIVO = arrayOf("vivo")
    private val ROM_XIAOMI = arrayOf("xiaomi")
    private val ROM_OPPO = arrayOf("oppo")
    private val ROM_LEECO = arrayOf("leeco", "letv")
    private val ROM_360 = arrayOf("360", "qiku")
    private val ROM_ZTE = arrayOf("zte")
    private val ROM_ONEPLUS = arrayOf("oneplus")
    private val ROM_NUBIA = arrayOf("nubia")
    private val ROM_COOLPAD = arrayOf("coolpad", "yulong")
    private val ROM_LG = arrayOf("lg", "lge")
    private val ROM_GOOGLE = arrayOf("google")
    private val ROM_SAMSUNG = arrayOf("samsung")
    private val ROM_MEIZU = arrayOf("meizu")
    private val ROM_LENOVO = arrayOf("lenovo")
    private val ROM_SMARTISAN = arrayOf("smartisan")
    private val ROM_HTC = arrayOf("htc")
    private val ROM_SONY = arrayOf("sony")
    private val ROM_GIONEE = arrayOf("gionee", "amigo")
    private val ROM_MOTOROLA = arrayOf("motorola")

    private const val VERSION_PROPERTY_HUAWEI = "ro.build.version.emui"
    private const val VERSION_PROPERTY_VIVO = "ro.vivo.os.build.display.id"
    private const val VERSION_PROPERTY_XIAOMI = "ro.build.version.incremental"
    private const val VERSION_PROPERTY_OPPO = "ro.build.version.opporom"
    private const val VERSION_PROPERTY_LEECO = "ro.letv.release.version"
    private const val VERSION_PROPERTY_360 = "ro.build.uiversion"
    private const val VERSION_PROPERTY_ZTE = "ro.build.MiFavor_version"
    private const val VERSION_PROPERTY_ONEPLUS = "ro.rom.version"
    private const val VERSION_PROPERTY_NUBIA = "ro.build.rom.id"
    private const val UNKNOWN = "unknown"


    @JvmStatic
    val isHuawei: Boolean by lazy { isRightRom(ROM_HUAWEI) }

    @JvmStatic
    val isXiaomi: Boolean by lazy { isRightRom(ROM_XIAOMI) }

    @JvmStatic
    val isVivo: Boolean by lazy { isRightRom(ROM_VIVO) }

    @JvmStatic
    val isOppo: Boolean by lazy { isRightRom(ROM_OPPO) }

    @JvmStatic
    val isSamsung: Boolean by lazy { isRightRom(ROM_SAMSUNG) }

    private fun isRightRom(names: Array<String>): Boolean {
        val brand: String = AppSystemInfo.board
        val manufacturer: String = AppSystemInfo.deviceManufacturer
        for (name in names) {
            if (brand.lowercase().contains(name.lowercase()) || manufacturer.lowercase().contains(name.lowercase())) {
                return true
            }
        }
        return false
    }

}