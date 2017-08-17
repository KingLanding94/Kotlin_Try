package com.example.xiaojun.kotlin_try.listener

import android.view.MotionEvent

/**
 * Created by XiaoJun on 2017/7/28.
 * Version 1.0.0
 */
interface FragmentTouchListener {
    fun onTouch(ev:MotionEvent):Boolean
}