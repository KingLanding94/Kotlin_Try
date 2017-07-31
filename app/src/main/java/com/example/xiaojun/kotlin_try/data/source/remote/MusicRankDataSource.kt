package com.example.xiaojun.kotlin_try.data.source.remote

import android.content.Context
import com.example.xiaojun.kotlin_try.data.bean.MusicRankResponseBean
import com.example.xiaojun.kotlin_try.data.bean.MusicRankSongResponseBean
import com.example.xiaojun.kotlin_try.service.BaiduMusicApi
import com.example.xiaojun.kotlin_try.mlibrary.RetrofitClient
import io.reactivex.Observable

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
class MusicRankDataSource(private val context: Context) {


    fun loadRankList():Observable<MusicRankResponseBean>?{
        val retrofit = RetrofitClient.getInstance(context, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        return apiService?.getRankList(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format,"baidu.ting.billboard.billCategory",1)
    }


    fun loadRankSongList(type:Int,size:Int,offset:Int):Observable<MusicRankSongResponseBean>?{
        val retrofit = RetrofitClient.getInstance(context, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        return apiService?.getRankSongList(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format,"baidu.ting.billboard.billList",
                type,size,offset)
    }

}