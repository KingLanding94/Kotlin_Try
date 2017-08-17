package com.example.xiaojun.kotlin_try.ui.activity.music

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.base.BaseActivity
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicLocalFragment
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.ToastUtil
import kotlinx.android.synthetic.main.activity_local_song_sheet.*

class LocalSongSheetActivity : BaseActivity() {


    private var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_song_sheet)
        setSupportActionBar(musicToolbar)
        musicToolbar.setNavigationOnClickListener(
                { finish() }
        )
        initView()
    }


    fun initView() {
        fragmentManager = supportFragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.localSongSheetContent, MusicLocalFragment())
        transaction.commit()
    }


    //显示音乐浮动按钮
    override fun showMusicStateFab(): Boolean {
        return true
    }

}
