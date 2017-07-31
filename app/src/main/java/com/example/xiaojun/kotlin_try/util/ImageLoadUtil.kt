package com.example.xiaojun.kotlin_try.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.xiaojun.kotlin_try.R

/**
 * Created by XiaoJun on 2017/7/21.
 * Version 1.0.0
 */
object ImageLoadUtil {

        fun loadByUrl(context: Context,imageView: ImageView ?,url: String){
            Glide.with(context).load(url)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.load_failed)
                    .crossFade()
                    .into(imageView)
        }


}