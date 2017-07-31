package com.example.xiaojun.kotlin_try.data.source.remote

import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.service.DoubanApi
import com.example.xiaojun.kotlin_try.mlibrary.RetrofitClient
import io.reactivex.Observable

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MovieListDataSource {
    var retrofit:RetrofitClient? = null
    init {
        retrofit = RetrofitClient.newInstance(App.getContext(), DoubanApi.BaseUrl)
    }
    fun loadTheaterMovie(start:Int):Observable<MovieDoubanResponseBean>?{
        val apiService = retrofit!!.create(DoubanApi::class.java)
        return apiService?.getTheaterMovie(start)
    }

    fun loadComingMovie(start:Int):Observable<MovieDoubanResponseBean>?{
        val apiService = retrofit!!.create(DoubanApi::class.java)
        return apiService?.getComingMovie(start)
    }

    fun loadTopgMovie(start:Int):Observable<MovieDoubanResponseBean>?{
        val apiService = retrofit!!.create(DoubanApi::class.java)
        return apiService?.getTopMovie(start)
    }
}