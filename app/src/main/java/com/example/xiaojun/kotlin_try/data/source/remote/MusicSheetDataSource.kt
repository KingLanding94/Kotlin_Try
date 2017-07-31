package com.example.xiaojun.kotlin_try.data.source.remote

import android.content.Context
import com.example.xiaojun.kotlin_try.data.bean.MusicSheetResponseBean
import com.example.xiaojun.kotlin_try.data.bean.MusicSheetSongResponseBean
import com.example.xiaojun.kotlin_try.service.BaiduMusicApi
import com.example.xiaojun.kotlin_try.mlibrary.RetrofitClient
import io.reactivex.Observable

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
/**
 * 加载歌单相关内容
 */
class MusicSheetDataSource(private val mContext:Context){


    fun loadSheetList(page_size:Int,page_no:Int): Observable<MusicSheetResponseBean>? {
        val retrofit = RetrofitClient.getInstance(mContext, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        return apiService?.getSheetList(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format,"baidu.ting.diy.gedan",page_size,page_no)
    }


    fun loadSheetSongList(listId:Int):Observable<MusicSheetSongResponseBean>?{
        val retrofit = RetrofitClient.getInstance(mContext, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        return apiService?.getSheetSongList(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format,"baidu.ting.diy.gedanInfo",listId)
    }


}