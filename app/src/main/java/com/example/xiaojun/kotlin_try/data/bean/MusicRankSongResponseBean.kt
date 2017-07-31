package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
data class MusicRankSongResponseBean(
        var song_list:List<SongBean.SongInfoForShow>?,
        var billboard:BillBoard?
) {
    /**
     * 封面图片的链接之前就已经加载过，现在部队它做储存
     */
    data class BillBoard(
            var billboard_songnum:String?,
            var name:String?,
            var comment:String?
    )

}