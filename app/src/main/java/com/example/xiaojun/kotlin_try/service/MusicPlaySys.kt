package com.example.xiaojun.kotlin_try.service

import com.example.xiaojun.kotlin_try.data.db.SongInfoBean

/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */
class MusicPlaySys(val song:SongInfoBean,val playInfo:PlayInfo){
    class PlayInfo( val isPlaying:Boolean,
                    val currPosition:Int)
}