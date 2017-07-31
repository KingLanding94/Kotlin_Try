package com.example.xiaojun.kotlin_try.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

/**
 * Created by XiaoJun on 2017/7/24.
 * Version 1.0.0
 */
class MediaPlayConnection: ServiceConnection {

    private var mBinder: MusicPlayService.MediaPlayBinder? = null
    override fun onServiceDisconnected(p0: ComponentName?) {
        mBinder = null
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        mBinder = p1 as MusicPlayService.MediaPlayBinder
    }

}