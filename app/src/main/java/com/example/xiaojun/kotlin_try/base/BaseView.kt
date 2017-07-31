package com.example.xiaojun.kotlin_try.base

/**
 * Created by Xiaojun on 2017/7/17.
 */
interface BaseView {

    /**
     * setData
     */
    fun onSuccess()

    /**
     * getDataFailed
     */
    fun onFailed()

    /**
     * doing getting data
     */
    fun onGoing()
}
