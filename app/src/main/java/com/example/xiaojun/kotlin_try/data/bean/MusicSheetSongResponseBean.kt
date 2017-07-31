package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
data class MusicSheetSongResponseBean(
        var listid:Int?,
        var pic_500:String?,
        var listenum:String?,
        var collectnum:String?,
        var desc:String?,
        var tag: String?,
        var content:List<SongBean.SongInfoForShow> ) {
}