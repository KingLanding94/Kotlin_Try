package com.example.xiaojun.kotlin_try.data.source

import android.content.Context
import android.database.Observable

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */
abstract class DataSource {

    /**
     * 加载数据
     */
    abstract fun loadData(context: Context):List<Any>

    /**
     * 加载更多Any
     */
    open fun loadMoreData(context: Context,offset:Int,count:Int){

    }
}