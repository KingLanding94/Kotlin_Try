package com.example.xiaojun.kotlin_try.ui.activity.movie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.bean.MovieBean
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_movie_play.*
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer



class MoviePlayActivity : AppCompatActivity() {

    var localVideo :MovieBean.Video? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_play)
        localVideo = intent.getSerializableExtra("video") as MovieBean.Video
        initView()
    }

    fun initView(){
        viewPlayer.setUp(localVideo?.path
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛")
//        viewPlayer.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
    }

    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }
}
