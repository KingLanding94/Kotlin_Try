package com.example.xiaojun.kotlin_try.data.db

/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */
class DB{
    companion object {
        var instance:DB? = null

        fun getOrCreateInstance():DB{
            if (instance == null){
                instance = DB()
            }
            return instance!!
        }
    }
}