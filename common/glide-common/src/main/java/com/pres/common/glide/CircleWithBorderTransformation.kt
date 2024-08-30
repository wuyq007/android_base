package com.pres.common.glide

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
import kotlin.math.min

class CircleWithBorderTransformation(
    private val borderWidth: Int,
    private val borderColor: Int
) : BitmapTransformation() {

    private val ID = "${BuildConfig.LIBRARY_PACKAGE_NAME}.CircleWithBorderTransformation"
    private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)

    private val borderPaint by lazy {
        Paint().apply {
            isDither = true
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = borderWidth.toFloat()
        }
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return circleCrop(pool, toTransform)
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap): Bitmap {
        val size = min(source.width, source.height) - borderWidth / 2
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squared = Bitmap.createBitmap(source, x, y, size, size)
        val result = pool[size, size, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        val borderRadius = r - borderWidth / 2
        canvas.drawCircle(r, r, borderRadius, borderPaint)
        return result
    }
}
