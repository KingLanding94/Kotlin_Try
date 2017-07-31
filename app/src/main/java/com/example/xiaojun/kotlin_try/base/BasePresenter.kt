package com.example.xiaojun.kotlin_try.base

/**
 * Created by Xiaojun on 2017/7/17.
 */
interface BasePresenter {

    /**
     * while getting data from local,start() is the same as onGoing()
     */
    fun start()

    fun onCompleted()

    fun onGoing()

    fun onFailed()

}