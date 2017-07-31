package com.example.xiaojun.kotlin_try.presenter

import com.example.xiaojun.kotlin_try.contact.TypeTabContact
import com.example.xiaojun.kotlin_try.util.Constant

/**
* Created by XiaoJun on 2017/7/17.
* Version 1.0.0
*/

class TypeTabPresenter(private val type:Int,private val view: TypeTabContact.View):TypeTabContact.Presenter {

    var list:List<String>? = null
    override fun start() {
        onGoing()
    }

    /**
     * get tabs from server or tabs from local
     */
    override fun submitTabItems():ArrayList<String> {
        return list as ArrayList<String>
    }

    override fun onCompleted() {
        view.onSuccess()
    }


    override fun onGoing() {
        view.onGoing()
        when(type){
            Constant.MOVIE->{
                list =  getMovieTabs()
            }
            Constant.MUSIC->{
                list = getMusicTabs()
            }
            Constant.BOOK->{
                list = getBookTabs()
            }
        }

        if (list != null){
            onCompleted()
        }else{
            onFailed()
        }
    }

    override fun onFailed() {
        view.onFailed()
    }

    /**
     * before we get our tabs,check tabs on server changed or not,
     * eh,now they are just constant
     */

    fun getMovieTabs():ArrayList<String>{
        val ret = ArrayList<String>()
        ret += arrayListOf("正在热播","即将上映","Top250", "本地视频")
        return ret
    }

    fun getMusicTabs():ArrayList<String>{
        val ret = ArrayList<String>()
        ret += arrayListOf("推荐","歌单","排行榜","电台" )
        return ret
    }

    fun getBookTabs():ArrayList<String>{
        val ret = ArrayList<String>()
        ret += arrayListOf("推荐","歌单","排行榜","电台" )
        return ret
    }
}