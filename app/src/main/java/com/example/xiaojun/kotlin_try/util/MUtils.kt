package com.example.xiaojun.kotlin_try.util

import android.content.Context
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri


/**
 * Created by Xiaojun on 2017/7/16.
 */
object MUtils {

    /**
     * 检测网络是否可用

     * @return
     */
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null && ni.isConnectedOrConnecting
    }

    /**
     * 获取屏幕宽度
     * @param context
     * *
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     * @param context
     * *
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * duration to time showing
     */
    fun millSecToMusicTime(duration:Int):String{
        var ret = ""
        val seconds = duration / 1000
        val minute = seconds/60
        val second = seconds%60
        if (minute < 10){
            ret = ret+"0"+minute + ":"
        }else{
            ret = ret+ minute+":"
        }
        if (second < 10){
            ret = ret+"0"+second
        }else{
            ret = ret+second
        }
        return ret
    }


    /**
     * 从歌曲里面加载缩略图
     */

    fun getBitMapFromSong(uri:String):Bitmap?{
        val mmr = MediaMetadataRetriever()
        val rawArt: ByteArray?
        val art: Bitmap?
        val bfo = BitmapFactory.Options()
        mmr.setDataSource(App.getContext(), Uri.parse(uri))
        rawArt = mmr.embeddedPicture
        if (null != rawArt)
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
        else
            art = null
        return art
    }


}