package com.example.xiaojun.kotlin_try.presenter

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.contact.MusicListContact
import com.example.xiaojun.kotlin_try.data.db.DBHelper
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.data.source.local.MusicLocalSource
import com.example.xiaojun.kotlin_try.util.Constant
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by XiaoJun on 2017/7/17.
 * Version 1.0.0
 */

/**
 * 对于多对多的关系，可以建立第三个表来联系两个表，但是那样的方式让让逻辑麻烦太多了，所以，浪费存储空间吧
 */
class MusicListPresenter(private val view:MusicListContact.View): MusicListContact.Presenter {

    var numbers = ArrayList<Int>()
    var icons = ArrayList<Int>()
    var titles = ArrayList<String>()
    var list = ArrayList<HashMap<String,Any>>()

    override fun submitData(): List<HashMap<String,Any>> {
        return list
    }

    override fun start() {
        onGoing()
    }

    override fun onCompleted() {
        view.onSuccess()
    }

    override fun onGoing() {
        view.onGoing()
        icons = setIcons()
        titles = setTitle()
        numbers = setNumbers()
        list = setList()

        if (list.size >0){
            onCompleted()
        }else{
            onFailed()
        }
    }

    override fun onFailed() {
        view.onFailed()
    }

    fun setList():ArrayList<HashMap<String,Any>>{
        val ret = ArrayList<HashMap<String,Any>>()
        for (i in 0..icons.size-1){
            val map = HashMap<String,Any>()
            map.put("icon",icons[i] as Any)
            map.put("title",titles[i] as Any)
            map.put("number",numbers[i] as Any)
            ret += map
        }
        return ret
    }

    /**
     *这部分比较麻烦，因为要扫描本地音乐的数据
     */
    fun setNumbers():ArrayList<Int>{
        val ret = arrayListOf(0,0)
        var localMusic = DBHelper.getTableSize(Constant.LOCALMUSIC)
        if (localMusic == 0){
            val source = MusicLocalSource()
            val list = source.getMusicList()
//            DBHelper.saveListToTable(list!!,Constant.LOCALMUSIC)     先不处理数据库问题了
            localMusic = list!!.size
        }
        ret.add(0,localMusic)
        var recentMusic = DBHelper.getTableSize(Constant.RECENTPLAY)
        ret.add(1,recentMusic)
        return ret
    }

    fun setIcons():ArrayList<Int>{
        val ret = arrayListOf(R.drawable.music_icn_local,R.drawable.music_icn_recent,R.drawable.music_icn_download,R.drawable.music_icn_artist)
        return ret
    }

    fun setTitle():ArrayList<String>{
        val ret = arrayListOf("本地音乐","最近播放","下载管理","歌手列表")
        return ret
    }

}