package com.example.xiaojun.kotlin_try.mlibrary

import android.content.Context
import com.example.xiaojun.kotlin_try.util.Config
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * okHttp还具有设置cache设置cookie等功能
 */
class RetrofitClient(context: Context,baseUrl: String) {
    var baseUrl: String? = null
    var context: Context? = null
    var mRetrofit: Retrofit? = null

    init {
        this.baseUrl = baseUrl
        this.context = context

        //创建okHttpClient
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(Config.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Config.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Config.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        //创建Retrofit
        mRetrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
    }
    companion object {
        var instance: RetrofitClient? = null

        fun getInstance(context: Context, baseUrl:String): RetrofitClient {
            if (instance == null){
                synchronized(RetrofitClient::class){
                    instance = RetrofitClient(context, baseUrl)
                }
            }
            return instance!!
        }

        /**
         * 当我们使用的url不再是默认的url时，此时需要新建实例
         */
        fun newInstance(context: Context,baseUrl: String): RetrofitClient {
            return RetrofitClient(context, baseUrl)
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return mRetrofit?.create(service)
    }


}