package com.example.xiaojun.kotlin_try.ui.activity.movie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.google.gson.Gson
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_show.*

class MovieShowActivity : AppCompatActivity() {

    private var movieBean : MovieDoubanResponseBean.MovieBeanInDouBan? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_show)
        movieBean = Gson().fromJson(intent.extras.get("movie") as String,MovieDoubanResponseBean.MovieBeanInDouBan::class.java)
        initView()
    }

    fun initView(){
        setSupportActionBar(movieShowToolbar)
        movieShowToolbar.setNavigationOnClickListener({
            onBackPressed()
        })
        movieTitle.text = movieBean?.title
        val rating = movieBean?.rating?.average
        ratingNum.text = rating.toString()
        Glide.with(this).load(movieBean?.images?.large).centerCrop().bitmapTransform(BlurTransformation(this,25)).crossFade(1000).into(movieBg)
        Glide.with(this).load(movieBean?.images?.large).centerCrop().into(posterCover)

    }
}
