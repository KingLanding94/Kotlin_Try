package com.example.xiaojun.kotlin_try.util

/**
 * Created by Xiaojun on 2017/7/16.
 */
object Constant {
    val TABDIVIDER = 4
    val MUSIC = 0
    val MOVIE = 1
    val BOOK = 2

    /**
     * 音乐播放列表有关
     */

    val LOCAL_SONG_SHEET_ID :Long = 0
    val RECENT_SONG_SHEET_ID :Long = 1

    /**
     * 显示歌单所用的数据，因为radio的sheet的显示共享同一个activity
     */
    val FROMRADIO = 1
    val FROMSHEET = 2
    val FROMRANK = 3

    /**
     * 当有多条数据的时候，单次加载的数据条目
     */

    val SONGSIZE = 100
    val LISTSIZE = 15

    /**
     * 控制音乐播放的action
     */
    val PLAYCONTROLACTION = "com.xiaojun.musicControl"

    val PLAYACTION = 1
    val PAUSEACTION = 2
    val NEXTACTION = 4
    val PREVACTION = 8
    val MODEACTION = 16

    val REQUESTINFO = 128
    val ISPLAYING = 256
    /**
     * 音乐播放模式
     */

    val MODE_LOOP = 1
    val MODE_SINGLE = 2
    val MODE_RANDOM = 3

    /**
     * 音乐播放通知活动刷新界面的action
     */
    val UPDATEVIEWACTION = "com.xiaojun.viewUpdate"
    val PLAYTIME = 100



    /**
     * 设置音乐播放列表
     */


    /**
     * 音乐来源
     */
    val FROMNET = 10
    val FROMLOCAL = 20



    val MAXSETPS = 1000


    /**
     * 电影相关
     */

    val NET = "net"
    val LOCAL = "local"

    val THEATER = "Theater"
    val COMING = "Coming"
    val Top250 = "Top250"


    val CONTROLTABACTION = "com.xiaojun.ControlTab"
    val HIDE_TAB = -1
    val SHOW_TAB = 1


    /**
     * 数据库相关
     */

    val LOCALMUSIC = "本地音乐"
    val FAVORITE = "我喜欢"
    val RECENTPLAY = "最近播放"

}