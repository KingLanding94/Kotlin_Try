package com.example.xiaojun.kotlin_try.service

import com.example.xiaojun.kotlin_try.data.bean.SongBean
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean

/**
 * Created by XiaoJun on 2017/7/24.
 * Version 1.0.0
 */
class PlayListChangedEvent(var songList:ArrayList<SongInfoBean>?,
                           var mgs:Int?) {


}