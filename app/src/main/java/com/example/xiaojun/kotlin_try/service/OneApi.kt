package com.example.xiaojun.kotlin_try.service

import com.example.xiaojun.kotlin_try.data.bean.MonthInOneBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by XiaoJun on 2017/7/30.
 * Version 1.0.0
 */
interface OneApi {
    companion object {
        val BaseUrl = "http://v3.wufazhuce.com:8000/api/"
    }

    /**
     * 获取这个月的图文内容
     * @param month 输入月份
     * @return
     */
    @GET("hp/bymonth/{month}")
    fun getListBean(@Path("month") month: String): Observable<MonthInOneBean>



}