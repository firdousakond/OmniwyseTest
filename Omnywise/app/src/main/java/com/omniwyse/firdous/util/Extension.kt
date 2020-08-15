package com.omniwyse.firdous.util
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.omniwyse.firdous.R

fun View.show (){
    visibility = View.VISIBLE
}

fun View.hide (){
    visibility = View.GONE
}

fun RecyclerView.initializeGridVertical(adapter: RecyclerView.Adapter<*>?, gridCount: Int = 1) {
    setAdapter(adapter)
    layoutManager = GridLayoutManager(context, gridCount, GridLayoutManager.VERTICAL, false)
}

fun RecyclerView.initializeVertical(adapter: RecyclerView.Adapter<*>?) {
    setAdapter(adapter)
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
}



fun ImageView.loadImageFromUrl(
    context: Context?,
    imageUrl: String) {
    val requestHeaders: com.bumptech.glide.load.model.Headers = LazyHeaders.Builder()
        .build()
    val glideUrl = GlideUrl(imageUrl, requestHeaders)
    Glide.with(context!!)
        .load(glideUrl).apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .dontTransform()
        .circleCrop()
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImageFromUrl.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any,
                target: Target<Drawable?>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {

                return false
            }
        })
        .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
        .into(this)
}




