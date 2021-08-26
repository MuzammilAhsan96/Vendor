package com.application.vendor.utils

import android.widget.ImageView
import com.application.vendor.R
import com.application.vendor.base.App
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object GlideUtils {



    fun loadImage(view: ImageView?, url: String?, w: Int, h: Int) {
        view?.let {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .override(w, h)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(it)
        }
    }


    fun loadImageFullWidth(view: ImageView?, url: String?) {

        view?.let {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(it)

        }

    }

    fun loadImageFullWidth(view: ImageView?, url: Int?) {

        view?.let {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(it)

        }

    }
}