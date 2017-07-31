package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
data class MusicRadioSongResponseBean(
        var error_code:Int?,
        var result:Result
) {

    data class Result(
            var channel:String?,
            var ch_name:String?,
            var count:String?,
            val songlist:List<SongBean.SongInfoForShow>
    )

}