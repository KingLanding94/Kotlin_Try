package com.example.xiaojun.kotlin_try.service

import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */

/**
 * 以下数据均是来源于豆瓣
 */
interface DoubanApi {

    companion object {
        val BaseUrl = "https://api.douban.com/"
    }

    /**
     * 得到影院当前热映电影
     * https://api.douban.com/v2/movie/in_theaters
     * @start:相当于查询偏移量
     */
    @GET("v2/movie/in_theaters")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getTheaterMovie(@Query("start") start:Int ):Observable<MovieDoubanResponseBean>

    /**
     * 得到即将上映的电影
     * https://api.douban.com/v2/movie/coming_soon
     */
    @GET("v2/movie/coming_soon")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getComingMovie(@Query("start") start:Int ):Observable<MovieDoubanResponseBean>

    /**
     * Top250
     * https://api.douban.com/v2/movie/top250
     */
    @GET("v2/movie/top250")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0)")
    fun getTopMovie(@Query("start") start:Int ):Observable<MovieDoubanResponseBean>

    /**
     *
     */


}