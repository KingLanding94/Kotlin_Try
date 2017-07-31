package com.example.xiaojun.kotlin_try

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xiaojun.kotlin_try.data.db.SongInfoDao
import com.example.xiaojun.kotlin_try.ui.activity.MainActivity
import com.example.xiaojun.kotlin_try.util.Constant
import kotlinx.android.synthetic.main.activity_splash.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN



class SplashActivity : AppCompatActivity() {

    val MOVE = 1
    val duration:Long = 2500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //设置全屏，从而完全沉浸式体验
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = option

        val typeFace:Typeface = Typeface.createFromAsset(assets, "fofbb_ital.otf")
        appName.typeface = typeFace
        val animation = AnimationUtils.loadAnimation(this,R.anim.splash)
        animation.fillAfter = true
        animation.duration = duration
        appName.animation = animation



        val handler = @SuppressLint("HandlerLeak")
        object :Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if(msg?.what == MOVE ){
                    startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                }
            }
        }
        handler.sendEmptyMessageDelayed(MOVE,duration)
    }


    /**
     * 如果这是第一次使用，数据库需初始化数据,现在数据库这部分内容先不做处理
     */
    fun initData(){
        //如果没有建立数据库，那么建立数据库
        val dao = SongInfoDao()
        if (!dao.isTableCreated(Constant.LOCALMUSIC)){
            dao.createSongTable(Constant.LOCALMUSIC)
            dao.createSongTable(Constant.RECENTPLAY)
            dao.createSongTable(Constant.FAVORITE)
        }
    }
}
