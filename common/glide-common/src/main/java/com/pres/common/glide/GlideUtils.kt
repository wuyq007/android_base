package com.pres.common.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pers.libs.base.app.AppConfig
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.*


object GlideUtils {

    /**
     * radius 圆角度数，单位 （px）
     */
    private fun load(request: RequestManager, imageView: ImageView, url: String, radius: Int) {
        if (radius > 0) {
            request.load(url).transition(DrawableTransitionOptions.withCrossFade()).transform(
                CenterCrop(), RoundedCorners(radius)
            ).into(imageView)
        } else {
            request.load(url).transition(DrawableTransitionOptions.withCrossFade()).centerCrop().into(imageView)
        }
    }

    /**
     * 加载圆形图片
     */
    private fun loadCircleImage(request: RequestManager, imageView: ImageView, url: String) {
        request.load(url).transition(DrawableTransitionOptions.withCrossFade()).optionalCircleCrop().into(imageView)
    }

    /**
     * 加载自定义圆角
     */
    private fun loadRadius(
        request: RequestManager,
        imageView: ImageView,
        url: String,
        topLeftRadius: Int,
        topRightRadius: Int,
        bottomRightRadius: Int,
        bottomLeftRadius: Int,
    ) {
        request.load(url).transition(DrawableTransitionOptions.withCrossFade()).transform(
            CenterCrop(), RoundedCornersTransformation(
                topLeftRadius.toFloat(), topRightRadius.toFloat(), bottomRightRadius.toFloat(), bottomLeftRadius.toFloat()
            )
        ).into(imageView)
    }


    /**
     * 加载 GIF， Glide 一般会通过文件扩展名识别GIF，正常情况下不需要特别指定：asGif()
     */
    private fun loadGif(request: RequestManager, imageView: ImageView, url: String, radius: Int = 0) {
        if (radius > 0) {
            request.asGif().load(url).transform(CenterCrop(), RoundedCorners(radius)).into(imageView)
        } else {
            request.asGif().load(url).centerCrop().into(imageView)
        }
    }


    fun load(imageView: ImageView?, url: String?, radius: Int = 0) {
        if (imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        load(Glide.with(imageView), imageView, url, radius)
    }

    fun load(view: View?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (view == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        load(Glide.with(view), imageView, url, radius)
    }

    fun load(activity: AppCompatActivity?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (activity == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        load(Glide.with(activity), imageView, url, radius)
    }

    fun load(fragment: Fragment?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (fragment == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        load(Glide.with(fragment), imageView, url, radius)
    }

    fun loadCircleImage(imageView: ImageView?, url: String?) {
        if (imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadCircleImage(Glide.with(imageView), imageView, url)
    }

    fun loadCircleImage(view: View?, imageView: ImageView?, url: String?) {
        if (view == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadCircleImage(Glide.with(view), imageView, url)
    }

    fun loadCircleImage(activity: AppCompatActivity?, imageView: ImageView?, url: String?) {
        if (activity == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadCircleImage(Glide.with(activity), imageView, url)
    }

    fun loadCircleImage(fragment: Fragment?, imageView: ImageView?, url: String?) {
        if (fragment == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadCircleImage(Glide.with(fragment), imageView, url)
    }

    fun loadRadius(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        topLeftRadius: Int,
        topRightRadius: Int,
        bottomRightRadius: Int,
        bottomLeftRadius: Int,
    ) {
        if (fragment == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadRadius(Glide.with(fragment), imageView, url, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
    }

    fun loadRadius(
        activity: AppCompatActivity?,
        imageView: ImageView?,
        url: String?,
        topLeftRadius: Int,
        topRightRadius: Int,
        bottomRightRadius: Int,
        bottomLeftRadius: Int
    ) {
        if (activity == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadRadius(Glide.with(activity), imageView, url, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
    }

    fun loadRadius(
        view: View?,
        imageView: ImageView?,
        url: String?,
        topLeftRadius: Int,
        topRightRadius: Int,
        bottomRightRadius: Int,
        bottomLeftRadius: Int
    ) {
        if (view == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadRadius(Glide.with(view), imageView, url, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
    }


    fun loadRadius(
        imageView: ImageView?,
        url: String?,
        topLeftRadius: Int,
        topRightRadius: Int,
        bottomRightRadius: Int,
        bottomLeftRadius: Int,
    ) {
        if (imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadRadius(
            Glide.with(imageView), imageView, url, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius
        )
    }


    fun loadGif(imageView: ImageView?, url: String?, radius: Int = 0) {
        if (imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadGif(Glide.with(imageView), imageView, url)
    }

    fun loadGif(view: View?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (view == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadGif(Glide.with(view), imageView, url)
    }

    fun loadGif(activity: AppCompatActivity?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (activity == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadGif(Glide.with(activity), imageView, url)
    }

    fun loadGif(fragment: Fragment?, imageView: ImageView?, url: String?, radius: Int = 0) {
        if (fragment == null || imageView == null || url == null || url.isEmpty()) {
            return
        }
        val url = getOSSResizeUrl(imageView, url)
        loadGif(Glide.with(fragment), imageView, url)
    }


    //  GlideUtils.loadWebp(
    //       imageView = binding.likedAnimIv,
    //       url = "android.resource://${context?.packageName}/drawable/${R.drawable.home_feed_anim_unliked}",
    //       callback = animationCallback
    //   )
    /**
     * assets
     * file:///android_asset/ic_fuqi.png
     * drawable
     * "android.resource://"+getPackageName()+"/drawable/"+R.drawable.ic_fuqi
     * "android.resource://"+getPackageName()+"/drawable/ic_fuqi"
     * raw
     * "android.resource://"+getPackageName()+"/raw/”+R.raw.ic_fuqi
     */
    fun loadWebp(imageView: ImageView, url: String?, loopCount: Int = WebpDrawable.LOOP_INTRINSIC, callback: AnimationCallback) {
        val transformation: Transformation<Bitmap> = CenterInside()
        Glide.with(imageView).load(url).optionalTransform(transformation)
            .optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(transformation))
            .addListener(object : RequestListener<Drawable> {

                override fun onResourceReady(
                    resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean
                ): Boolean {
                    if (resource is WebpDrawable) {
                        resource.loopCount = loopCount
                        resource.registerAnimationCallback(object : AnimationCallback() {
                            override fun onAnimationStart(drawable: Drawable?) {
                                super.onAnimationStart(drawable)
                                callback.onAnimationStart(drawable)
                            }

                            override fun onAnimationEnd(drawable: Drawable?) {
                                super.onAnimationEnd(drawable)
                                callback.onAnimationEnd(drawable)
                                resource.unregisterAnimationCallback(this)
                            }
                        })
                    }
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }
            }).into(imageView)
    }


    /**
     * 自定义边框的圆形图片
     */
    fun loadCircleBorder(imageView: ImageView, url: String?, borderWidth: Int, @ColorInt borderColor: Int) {
        val context = imageView.context
        Glide.with(context).load(url).centerCrop().transform(CircleWithBorderTransformation(borderWidth, borderColor)).into(imageView)
    }


    /**
     * 加载高斯模糊图片
     */
    fun loadBlurImage(imageView: ImageView, url: String?, blurRadius: Int = 50) {
        val context = imageView.context
        // 高斯模糊处理
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(BlurTransformation(blurRadius))).into(imageView)
    }


    private fun isNetworkImage(url: String?): Boolean {
        return if (url == null || url.isEmpty()) {
            false
        } else {
            url.startsWith("http://") || url.startsWith("https://")
        }
    }

    /**
     *
     */
    private fun getOSSResizeUrl(imageView: ImageView, url: String): String {
//        //是否是网络图片
//        if (!isNetworkImage(url)) {
//            return url
//        }
//        val width = imageView.width
//        val height = imageView.height
//        var url = url
//        if (width > 0 && height > 0) {
        //超大图片会失败，这里不使用 image/resize
//            val ossResize = "x-oss-process=image/resize,m_fill,h_$height,w_$width"
//            url = if (url.contains("?")) {
//                "$url&$ossResize"
//            } else {
//                "$url?$ossResize"
//            }
//        }
//        LogUtils.e("url:", url)
        return url
    }


    /**
     * 取消加载，并且清除目标缓存
     */
    fun clearCache(view: View?, imageView: ImageView?) {
        if (view == null || imageView == null) {
            return
        }
        Glide.with(view).clear(imageView)
    }

    fun clearCache(activity: AppCompatActivity?, imageView: ImageView?) {
        if (activity == null || imageView == null) {
            return
        }
        Glide.with(activity).clear(imageView)
    }

    fun clearCache(fragment: Fragment?, imageView: ImageView?) {
        if (fragment == null || imageView == null) {
            return
        }
        Glide.with(fragment).clear(imageView)
    }


    /**
     * 清除 Glide 的所有缓存
     */
    fun removeAllCache() {
        if (AppConfig.isMainThread()) {
            // 清除内存缓存
            Glide.get(AppConfig.application).clearMemory()
            // 清除磁盘缓存
            Glide.get(AppConfig.application).clearDiskCache()
        } else {
            val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            coroutineScope.launch {
                // 清除内存缓存
                Glide.get(AppConfig.application).clearMemory()
                // 清除磁盘缓存
                Glide.get(AppConfig.application).clearDiskCache()
                coroutineScope.cancel()
            }
        }
    }

}