package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/24.
 * Version 1.0.0
 */
class MusicPlayResponseBean(
        var songinfo:SongBean?,
        var bitrate:Bitrate
) {
    data class Bitrate(
            var free:Int?,
            var song_file_id:Int?,
            var file_size:Int?,
            var file_extension:String?,
            var file_bitrate:Int?,
            var file_link:String?
    )
}