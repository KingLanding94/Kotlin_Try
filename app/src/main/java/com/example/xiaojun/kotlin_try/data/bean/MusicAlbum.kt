package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
data class MusicAlbum(
        var album_id:String?,
        var author:String?,
        var title: String?,
        var publishcompany: String?,
        var country:String?,
        var language:String?,
        var songs_total:String?,
        var info:String?,
        var publishtime:String?,
        var pic_small: String?,
        var pic_big: String?,
        var artist_id: String?,
        var listen_num: String?,
        var songList: List<SongBean>?
) {
}