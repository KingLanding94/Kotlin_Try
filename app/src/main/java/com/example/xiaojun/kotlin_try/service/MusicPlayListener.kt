package com.example.xiaojun.kotlin_try.service

/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
interface MusicPlayListener {
    fun onPlaying(progress:Int)

    fun onSongChanged()
}