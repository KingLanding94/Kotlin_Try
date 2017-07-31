package com.example.xiaojun.kotlin_try.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.google.gson.Gson

/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */
class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_show)
    }

}