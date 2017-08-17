package com.example.xiaojun.kotlin_try.listener

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
interface DownloadListener {

    fun onProgress(progress:Int)

    fun onComplete()

    fun onPause()

    fun onFailed()

    fun onCanceled()


}