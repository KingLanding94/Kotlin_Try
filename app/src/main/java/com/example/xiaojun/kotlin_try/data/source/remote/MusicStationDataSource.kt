package com.example.xiaojun.kotlin_try.data.source.remote

import android.content.Context
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioSongResponseBean
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioStationResponseBean
import com.example.xiaojun.kotlin_try.service.BaiduMusicApi
import com.example.xiaojun.kotlin_try.mlibrary.RetrofitClient
import io.reactivex.Observable

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * DataSource 类的目的是减轻presenter的压力，因为presenter需要同view和model同时打交道
 */
class MusicStationDataSource(private val context: Context) {


    fun loadData(context: Context):Observable<MusicRadioStationResponseBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, BaiduMusicApi.MusicBase)
        val apiService = retrofitClient.create(BaiduMusicApi::class.java)
        return apiService?.getRadioStations(BaiduMusicApi.RadionFrom, BaiduMusicApi.Version, BaiduMusicApi.Format,"baidu.ting.radio.getCategoryList")
    }

    /**
     * pn:页数
     * size：一次加载的条数
     */
    fun loadSongInStation(pn:Int,size:Int,channelName:String):Observable<MusicRadioSongResponseBean>?{
        val retrofitClient = RetrofitClient.getInstance(context, BaiduMusicApi.MusicBase)
        val apiService = retrofitClient.create(BaiduMusicApi::class.java)
        return apiService?.getStationSongs(BaiduMusicApi.RadionFrom, BaiduMusicApi.Version, BaiduMusicApi.Format,"baidu.ting.radio.getChannelSong",pn,size,channelName)
    }

}