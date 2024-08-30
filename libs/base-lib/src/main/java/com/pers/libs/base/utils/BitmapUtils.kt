package com.pers.libs.base.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.pers.libs.base.app.AppConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

object BitmapUtils {

    /**
     * Drawable 转 Bitmap
     */
    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * ByteArray 转 Bitmap
     */
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    /**
     * 资源图片 转 Bitmap
     */
    fun resourcesToBitmap(@DrawableRes resId: Int): Bitmap? {
        return BitmapFactory.decodeResource(AppConfig.application.resources, resId)
    }

    /**
     * 本地图片路径 FilePath 转 Bitmap
     */
    fun filePathToBitmap(filePath: String): Bitmap? {
        return BitmapFactory.decodeFile(filePath)
    }

    /**
     * 保存 Bitmap 到本地
     */
    fun saveBitmapToFile(bitmap: Bitmap, filePath: String): Boolean {
        val file = File(filePath)
        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * bitmap 转 ByteArray
     */
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) // 可以选择其他格式，如JPEG
        return stream.toByteArray()
    }

    /**
     * 下载图片 返回bitmap
     */
    suspend fun downloadImageFromUrlByBitmap(url: String): Bitmap? {
        return downloadImageFromUrl(url)?.let { byteArrayToBitmap(it) }
    }


    suspend fun downloadImageFromUrl(url: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                inputStream.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    byteArrayOutputStream.toByteArray()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * 压缩图片
     * @param minSize: 短边的最小值，比如 1000 * 1200 的图片，将会转换成  100 * 120； 1200 * 1000 => 120*100
     */
    fun createScaledBitmap(bitmap: Bitmap, minSize: Int = 100): Bitmap {
        val dstWidth: Int
        val dstHeight: Int
        if (bitmap.width > bitmap.height) {
            if (bitmap.height <= minSize) {
                return bitmap
            }
            dstHeight = minSize
            dstWidth = minSize * bitmap.width / bitmap.height
        } else {
            if (bitmap.width <= minSize) {
                return bitmap
            }
            dstWidth = minSize
            dstHeight = minSize * bitmap.height / bitmap.width
        }
        return Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
    }

    /**
     * 压缩图片,采样率压缩
     */
    fun createScaledBitmap(bitmapByteArray: ByteArray, minSize: Int = 100): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.size, options);
        options.inSampleSize = calculateInSampleSize(options, minSize);
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.size, options)
    }


    /**
     * 计算采样率
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, minSize: Int = 100): Int {
        // 原始图像的宽度和高度
        val height = options.outHeight
        val width = options.outWidth
        //采样率
        val inSampleSize = if (width > height) {
            if (height <= minSize) {
                1
            } else {
                height / minSize
            }
        } else {
            if (width <= minSize) {
                1
            } else {
                width / minSize
            }
        }
        return inSampleSize
    }


    /**
     * 给Bitmap添加圆角和边框
     *
     * @param borderWidth 边框宽度
     * @param borderColor 边框颜色
     * @param cornerRadius 圆角度数
     */
    fun addBorderAndRoundCorners(srcBitmap: Bitmap, borderWidth: Float, @ColorInt borderColor: Int, cornerRadius: Float): Bitmap? {
        // 创建一个带透明背景的新 Bitmap
        val output = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        // 绘制圆角矩形
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = RectF(0F, 0F, srcBitmap.width.toFloat(), srcBitmap.height.toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
        // 设置 SRC_IN 模式，这样我们可以只显示原始图像和圆角矩形的交集部分
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(srcBitmap, 0F, 0F, paint)
        paint.xfermode = null
        // 绘制边框
        if (borderWidth > 0) {
            paint.style = Paint.Style.STROKE
            paint.color = borderColor
            paint.strokeWidth = borderWidth
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
        }
        return output
    }

}