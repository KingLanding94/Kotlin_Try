package com.example.xiaojun.kotlin_try.ui.activity.movie

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.base.BaseActivity
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.util.ToastUtil
import com.google.gson.Gson
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_show.*

class MovieShowActivity : BaseActivity() {

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

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        movieShowCollTool?.title = movieBean?.title
        //通过CollapsingToolbarLayout修改字体颜色
        movieShowCollTool?.setExpandedTitleColor(Color.TRANSPARENT)//设置还没收缩时状态下字体颜色
        movieShowCollTool?.setCollapsedTitleTextColor(Color.WHITE)//设置收缩后Toolbar上字体的颜色

        movieTitle.text = movieBean?.title
        val rating = movieBean?.rating?.average
        ratingNum.text = rating.toString()
        Glide.with(this).load(movieBean?.images?.large).centerCrop().bitmapTransform(BlurTransformation(this,25)).crossFade(1000).into(movieBg)
        Glide.with(this).load(movieBean?.images?.large).centerCrop().into(posterCover)
        if (movieBean == null || movieBean!!.casts == null){
            ToastUtil.shortShow("未能加载到主演信息")
        }else{
            val actors = ArrayList<View>()
            for (i in movieBean!!.casts!!){
                val view = LayoutInflater.from(this).inflate(R.layout.item_movie_star,null)
                val images:ImageView = view.findViewById(R.id.cover)
                val name:TextView = view.findViewById(R.id.name)
                Glide.with(this).load(i.avatars?.large).into(images)
                name.text = i.name
                actors.add(view)
            }
            actorBanner.setViews(actors)
        }
    }
}