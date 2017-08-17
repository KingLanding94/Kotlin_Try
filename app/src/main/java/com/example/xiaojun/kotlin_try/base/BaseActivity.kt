package com.example.xiaojun.kotlin_try.base

import android.app.ActionBar
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.service.MusicPlayingStateEvent
import com.example.xiaojun.kotlin_try.ui.activity.music.MusicPlayActivity
import com.example.xiaojun.kotlin_try.util.ActivityCollector
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.util.Constant
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */
open class BaseActivity:AppCompatActivity() {


    private var animationDrawble: AnimationDrawable? = null
    private val mReceiver = UpdateViewReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        ActivityCollector.addElement(this) //退出应用使用

        if (!showMusicStateFab()){
            musicStateFab.visibility = View.GONE
        }else{
            musicStateFab.setOnClickListener {
                startActivity(Intent(App.getContext(), MusicPlayActivity::class.java))
            }
            //音乐播放时更新界面使用
            val filter = IntentFilter()
            filter.addAction(Constant.UPDATEVIEWACTION)
            registerReceiver(mReceiver, filter)
            animationDrawble = musicStateFab.drawable as AnimationDrawable
            EventBus.getDefault().register(this)  //新建界面或者返回界面时用来刷新音乐播放

        }
    }

    override fun onResume() {
        super.onResume()

        //如果设置了音乐浮动按钮，那么每次创建界面要请求状态刷新
        if (showMusicStateFab()){
            val intent = Intent()
            intent.action = Constant.PLAYCONTROLACTION
            intent.putExtra("what", Constant.ISPLAYING)
            sendBroadcast(intent)
        }
    }


    //为子类activity设置主界面使用的
    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID,null,false)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        baseContentView.addView(view,lp)
    }

    //默认不显示音乐播放浮动按钮
    open fun showMusicStateFab():Boolean{
        return false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPlayingState(event: MusicPlayingStateEvent) {
        if (event.isPlaying) {
            if (animationDrawble != null && !animationDrawble!!.isRunning)
                animationDrawble?.start()
        } else {
            if (animationDrawble != null && animationDrawble!!.isRunning)
                animationDrawble?.stop()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (showMusicStateFab()){
            unregisterReceiver(mReceiver)  //销毁注册
            EventBus.getDefault().unregister(this)
        }

        ActivityCollector.removeElement(this)
    }

    inner class UpdateViewReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val WHAT = p1!!.extras.getInt("what")
            when (WHAT) {
                Constant.PLAYACTION -> {
                    animationDrawble?.start()
                }
                Constant.PAUSEACTION -> {
                    animationDrawble?.stop()
                }
            }
        }
    }

    //监听来电广播，和音乐播放广播，当这两种情况产生任何一种的时候，我们需要暂停当前的播放


}