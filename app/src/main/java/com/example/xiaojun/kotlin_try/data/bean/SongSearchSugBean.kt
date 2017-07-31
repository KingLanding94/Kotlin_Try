package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */

/**
 *   bitrate_fee	"{\"0\":\"0|0\",\"1\":\"0|0\"}"
 *   weight	"16960"
 *   songname	"海阔天空"
 *   songid	"87859296"
 *   has_mv	"0"
 *   yyr_artist	"0"
 *   resource_type_ext	"0"
 *   artistname	"Beyond"
 *   info	""
 *   resource_provider	"1"
 *   control	"0000000000"
 *   encrypted_songid	"57081DD4AF1F0859549A7E"
 */
data class SongSearchSugBean(
           var weight:String?,
           var songname:String?,
           var songid:String?,
           var has_mv:String?,
           var artistname:String?
)