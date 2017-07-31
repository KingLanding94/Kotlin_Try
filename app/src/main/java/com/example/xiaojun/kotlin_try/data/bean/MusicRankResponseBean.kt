package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
data class MusicRankResponseBean(
                    var content: List<MusicRank>?,
                    var error_code:Int?
                     ) {
    /**
     * 只描述简单的几条内容就好了，我们想知道详情时可以点开排行榜，然后查询
     */

    data class MusicRank(    var name:String?,
                              var type:Int?,
                              var count:Int?,//里面有几首歌曲
                              var comment:String?,
                              var pic_s260:String?,
                              var pic_s192: String?,
                              var content:List<FrontSong>)

    data class FrontSong(
            var title:String?,
            var author:String?
    )
}