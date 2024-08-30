package com.pres.common.glide

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 自定义各个圆角,边框
 */
class RoundedCornersTransformation(
    private val topLeftRadius: Float,
    private val topRightRadius: Float,
    private val bottomRightRadius: Float,
    private val bottomLeftRadius: Float,
) : BitmapTransformation() {

    private val ID = "${BuildConfig.LIBRARY_PACKAGE_NAME}.RoundedCornersTransformation"
    private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int
    ): Bitmap {

        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        // Create a rounded rectangle path with the given radii
        val path = Path().apply {
            addRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()), floatArrayOf(
                    topLeftRadius,
                    topLeftRadius,
                    topRightRadius,
                    topRightRadius,
                    bottomRightRadius,
                    bottomRightRadius,
                    bottomLeftRadius,
                    bottomLeftRadius
                ), Path.Direction.CW
            )
        }

        // Clip the canvas with the rounded rectangle path
        canvas.clipPath(path)

        // Draw the original bitmap onto the canvas
        canvas.drawBitmap(toTransform, 0f, 0f, paint)

        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        return other is RoundedCornersTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

}
