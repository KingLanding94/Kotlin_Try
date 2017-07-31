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
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicLocalFragment
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.ToastUtil
import kotlinx.android.synthetic.main.activity_local_song_sheet.*
class LocalSongSheetActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_READ_STORAGE = 1
    private var fragmentManager: FragmentManager? = null
    private var animationDrawble: AnimationDrawable? = null
    private val mReceiver = UpdateViewReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_song_sheet)
        /**
         * 注册广播
         */
        val filter = IntentFilter()
        filter.addAction(Constant.UPDATEVIEWACTION)
        registerReceiver(mReceiver, filter)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_REQUEST_READ_STORAGE);
        }else{

        }
        initView()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    ToastUtil.shortShow("抱歉，查看本地内容需要您的权限")
                    finish()
                }
                return
            }
        }
    }

    fun initView(){
        fragmentManager = supportFragmentManager
        val transaction =  fragmentManager!!.beginTransaction()
        transaction.add(R.id.localSongSheetContent, MusicLocalFragment())
        transaction.commit()
        musicStateFab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
//        animationDrawble = musicStateFab.drawable as AnimationDrawable
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    inner class UpdateViewReceiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val WHAT = p1!!.extras.getInt("what")
            when(WHAT){
                Constant.PLAYACTION->{
                    animationDrawble?.start()
                }
                Constant.PAUSEACTION->{
                    animationDrawble?.stop()
                }
            }
        }
    }



}
