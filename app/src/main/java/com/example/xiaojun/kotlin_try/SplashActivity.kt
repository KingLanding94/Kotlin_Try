package com.example.xiaojun.kotlin_try

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.xiaojun.kotlin_try.data.db.SongInfoDao
import com.example.xiaojun.kotlin_try.ui.activity.MainActivity
import com.example.xiaojun.kotlin_try.util.Constant
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hello_tv.setOnClickListener({
            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        })

        //如果没有建立数据库，那么建立数据库
        val dao = SongInfoDao()
        if (!dao.isTableCreated(Constant.LOCALMUSIC)){
            dao.createSongTable(Constant.LOCALMUSIC)
            dao.createSongTable(Constant.RECENTPLAY)
            dao.createSongTable(Constant.FAVORITE)
        }
    }


    /**
     * 如果这是第一次使用，数据库需初始化数据
     */
    fun initData(){

    }
}
