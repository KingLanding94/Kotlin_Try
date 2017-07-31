package com.example.xiaojun.kotlin_try.util

import android.widget.Toast

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
object ToastUtil {

    fun shortShow(msg:String){
        show(msg,false)
    }

    fun longShow(msg:String){
        show(msg)
    }

    private fun show(msg:String,long:Boolean = true){
        val length:Int?
        if (long)
            length = Toast.LENGTH_LONG
        else
            length = Toast.LENGTH_SHORT
        Toast.makeText(App.getContext(), msg, length).show()
    }
}