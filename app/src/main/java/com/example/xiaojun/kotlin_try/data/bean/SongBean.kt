package com.example.xiaojun.kotlin_try.data.bean

import com.example.xiaojun.kotlin_try.util.Constant
import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * parameter has the same mean as their names
 */
data class SongBean(
        var artist_id: String?,
        var pic_big: String?,
        var pic_small: String?,
        var lrclink: String?, //为下载使用
        var has_mv_mobile: Int?,
        var title: String?,
        var song_id: String?,
        var ting_uid: String?,
        var author: String?,
        var album_id: String?,
        var album_title: String?) {

    data class SongLrc(var lrcContent: String?,
                       var lrcPath: String?,
                       var title: String?)


    /**
     * artist = author
     * songid = song_id
     * ting_uid = artist_id
     * 电台返回的数据没有album信息
     */
    data class SongInfoForShow(
            var title: String?,

            var author: String?,
            var artist: String?,

            var album_title: String?,

            var songid: String?,
            var song_id: String?,

            var album_id: String?,
            var ting_uid: String?,
            var artist_id: String?,

            var pic_big: String?,
            var thumb:String?
    )

    /**
     * 其实还需要songid 和 alblumid等信息，因为用户可能还会搜索音乐相关信息
     * 现在这个bean类同时作为了EventBus的事件类发送给activty作显示用
     */


    data class SongInfoForPlay(
            var songId: Long? = 0, //这个songId是用来存到本地数据库做为唯一标识的
            var from: Int = Constant.FROMLOCAL,
            var title: String? = "",
            var artist: String? = "",
            var album: String? = "",
            var coverPath: String? = "",
            var lyrPath: String? = "",
            var songPath: String? = "",
            var coverLink: String? = "",
            var lyrLink: String? = "",
            var songLink: String? = ""
    )
}