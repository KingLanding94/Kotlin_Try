package com.example.xiaojun.kotlin_try.util

import android.app.Service
import android.media.AudioManager

/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
object VolumeUtils {
    val audioManager = App.getContext().getSystemService(Service.AUDIO_SERVICE) as AudioManager
    val type = AudioManager.STREAM_MUSIC
    val maxVolume = audioManager.getStreamMaxVolume(type)


    fun increase(){
        audioManager.adjustStreamVolume(type, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
    }

    fun decrease(){
        audioManager.adjustStreamVolume(type, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
    }

    fun setVolume(v:Int){
        val set = ((v/100.0)* maxVolume).toInt()
        audioManager.setStreamVolume(type,set,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
    }

    fun getCurrVolume():Int{
        return (100* audioManager.getStreamVolume(type))/ maxVolume
    }
}