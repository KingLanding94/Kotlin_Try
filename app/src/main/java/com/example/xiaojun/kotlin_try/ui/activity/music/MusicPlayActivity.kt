package com.example.xiaojun.kotlin_try.ui.activity.music

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Point
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.service.MusicPlayListener
import com.example.xiaojun.kotlin_try.service.MusicPlayService
import com.example.xiaojun.kotlin_try.service.MusicPlaySys
import com.example.xiaojun.kotlin_try.ui.widget.AlbumView
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.MUtils
import com.example.xiaojun.kotlin_try.util.VolumeUtils
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_music_play.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MusicPlayActivity : AppCompatActivity(), ServiceConnection, View.OnClickListener, MusicPlayListener {
    /**
     * 与音乐播放服务绑定,在播放活动里面，播放并不管理当前的播放列表
     * 当这个活动被点开之后，此界面向playService发送请求消息，然后playservice把
     * 当前的播放信息回执给activity
     */
    private var mBinder: MusicPlayService.MediaPlayBinder? = null
    private var musicPlayService: MusicPlayService? = null
    private var musicMode = Constant.MODE_LOOP
    private var isFavorite = false    //现在只考虑的是以前未添加喜欢的音乐
    private var currSongInfo: SongInfoBean? = null
    private val modeIcon = arrayListOf<Int>(R.drawable.loop, R.drawable.single, R.drawable.random)
    private var isBgSet = false
    private var isPlaying = false
    private var handler: Handler? = null
    private var albums = ArrayList<AlbumView>()
    private var albumPagerAdapter:PagerAdapter? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_play)

        EventBus.getDefault().register(this)
        val intent = Intent(this, MusicPlayService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
        initView()
    }

    /**
     * 每次切换到activity的时候都要请求当前播放信息
     */
    override fun onResume() {
        super.onResume()
        sysPlayView()
    }


    //音量条，音乐进度条，当前播放音乐名称，当前播放状态
    fun sysPlayView() {
        //设置音量条和发送广播
        controlPlay(Constant.REQUESTINFO)
        volumeSeekBar.progress = VolumeUtils.getCurrVolume()
        isBgSet = false
        Log.e("play", " onResume")
    }

    fun initView() {

        val views = ArrayList<AlbumView>()
        for (i in 0..1){
            val view = AlbumView(this)
            view.setAlbum(null)
            views.add(view)
        }

        albumPagerAdapter = MusicAlbumPageAdapter()
        albumViewPager.adapter = albumPagerAdapter
        albumViewPager.addOnPageChangeListener(PageChanged())

        setSupportActionBar(songPlayToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        songPlayToolbar?.setNavigationOnClickListener({
            onBackPressed()
        })
        playMode.setOnClickListener(this)
        playPrev.setOnClickListener(this)
        playOrStop.setOnClickListener(this)
        playNext.setOnClickListener(this)
        playSongList.setOnClickListener(this)
        playLove.setOnClickListener(this)
        playDownload.setOnClickListener(this)
        playComment.setOnClickListener(this)
        playMore.setOnClickListener(this)
        mainContent.setOnClickListener(this)
        mainContentLyr.setOnClickListener(this)
        albumViewPager.setOnTouchListener(ViewPagerTouch())

        musicSeekBar.setOnDragListener(object :View.OnDragListener{
            override fun onDrag(p0: View?, p1: DragEvent?): Boolean {

                return true
            }
        })
        volumeSeekBar.setOnSeekBarChangeListener(SeekBarChangeListener())

//        SysSeekBar().start()
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                handler?.sendEmptyMessageDelayed(1,1000);
                super.handleMessage(msg)
                if (msg?.what == 1 && musicPlayService != null && musicPlayService!!.isPlaying()) {
                    val currTime = musicPlayService!!.getCurrDuration()
                    timePlayed.text = MUtils.millSecToMusicTime(currTime)
                    musicSeekBar.progress = (Constant.MAXSETPS * currTime / currSongInfo!!.duration)
                }
            }
        }
        handler?.sendEmptyMessageDelayed(1,100)

    }

    override fun onServiceDisconnected(p0: ComponentName?) {

    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        mBinder = p1 as MusicPlayService.MediaPlayBinder
        musicPlayService = mBinder!!.getMediaPlayService()
        musicPlayService!!.setPlayingListener(this)

        albums.clear()
        //同步播放列表
        for (i in musicPlayService!!.getSongList()){
            val album = AlbumView(this)
            album.setAlbum(i)
            albums.add(album)
        }
        albumPagerAdapter?.notifyDataSetChanged()
    }

    /**
     * playService返回的播放信息
     * 音乐进度条，当前播放音乐名称，当前播放状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayListChange(event: MusicPlaySys) {
        Log.e("playing ","seeking")
        currSongInfo = event.song
        val playInfo = event.playInfo
        //设置歌曲信息
        songName.text = event.song.title
        artistName.text = event.song.artist

        if (!isBgSet) {
            Log.e("setImage", " 0" + currSongInfo.toString())
            if (currSongInfo!!.from == Constant.FROMLOCAL) {
                val bitmap = MUtils.getBitMapFromSong(currSongInfo!!.songPath)
                if (bitmap != null) {
                    albumArt.setImageBitmap(bitmap)
                    isBgSet = true
                }
            }
            if (!isBgSet && currSongInfo!!.coverPath != null) {
                setBackGround(currSongInfo!!.coverPath)
            }
            if (!isBgSet && currSongInfo!!.coverLink != null) {
                Log.e("setImage", " 3")
                setBackGround(currSongInfo!!.coverLink)
            }
        }
        allTime.text = MUtils.millSecToMusicTime(currSongInfo!!.duration)

        //设置播放信息(当前是否正在播放，当前播放的多少，当前唱针的位置)
        if (playInfo.isPlaying) {
            playOrStop.setImageResource(R.drawable.pause)
            timePlayed.text = MUtils.millSecToMusicTime(playInfo.currPosition)
            musicSeekBar.progress = (Constant.MAXSETPS * playInfo.currPosition / currSongInfo!!.duration)
            if (needle.rotation < 0) //表示在暂停
                needleToPlay()
            isPlaying = true
        } else {
            playOrStop.setImageResource(R.drawable.play)
            if (needle.rotation > -20)  //在播放的位置上
                needleToPause()
            isPlaying = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
        EventBus.getDefault().unregister(this)
        Log.e("MusicPlayActivity", "destroy")
//        toSys = false

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.playOrStop -> {
                doWithPlayOrPause()
            }
            R.id.playPrev -> {
                controlPlay(Constant.PREVACTION)
            }
            R.id.playNext -> {
                controlPlay(Constant.NEXTACTION)
            }
            R.id.playMode -> {
                musicMode++
                if (musicMode > Constant.MODE_RANDOM) {
                    musicMode %= Constant.MODE_RANDOM
                }
                controlPlay(Constant.MODEACTION + musicMode)
                playMode.setImageResource(modeIcon[musicMode - 1])
            }
            R.id.playSongList -> {

            }
            R.id.playLove -> {
                isFavorite = !isFavorite
                if (isFavorite) {
                    playLove.setImageResource(R.drawable.ic_action_cancle_love)
                    playLove.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_set_love))
                } else {
                    playLove.setImageResource(R.drawable.ic_action_love)
                }
            }
            R.id.playDownload -> {

            }
            R.id.playComment -> {

            }
            R.id.mainContent -> {
                mainContent.visibility = View.GONE
                mainContentLyr.visibility = View.VISIBLE
            }
            R.id.mainContentLyr -> {
                mainContentLyr.visibility = View.GONE
                mainContent.visibility = View.VISIBLE
            }

        }
    }

    fun controlPlay(order: Int) {
        val intent = Intent()
        intent.action = Constant.PLAYCONTROLACTION
        intent.putExtra("what", order)
        sendBroadcast(intent)
    }

    fun doWithPlayOrPause() {
        val intent = Intent()
        intent.action = Constant.PLAYCONTROLACTION
        if (musicPlayService!!.isPlaying()) {  //暂停操作
            val animation = AnimationUtils.loadAnimation(this@MusicPlayActivity, R.anim.needle_to_pause)
            animation.fillAfter = true
            needle.startAnimation(animation)
            intent.putExtra("what", Constant.PAUSEACTION)
            playOrStop.setImageResource(R.drawable.play)
            isPlaying = false
        } else {
            val animation = AnimationUtils.loadAnimation(this@MusicPlayActivity, R.anim.needle_to_play)
            animation.fillAfter = true
            needle.startAnimation(animation)
            intent.putExtra("what", Constant.PLAYACTION)
            playOrStop.setImageResource(R.drawable.pause)
            isPlaying = true
        }
        sendBroadcast(intent)
    }

    override fun onPlaying(progress: Int) {

    }

    override fun onSongChanged() {
        isBgSet = false
    }

    fun setBackGround(uri: String) {
        isBgSet = true
        Glide.with(this)
                .load(uri)
                .bitmapTransform(BlurTransformation(this, 25))
                .crossFade(1000)
                .error(R.drawable.login_bg)
                .into(albumArt)
    }

    fun needleToPlay() {
        val animation = AnimationUtils.loadAnimation(this@MusicPlayActivity, R.anim.needle_to_play)
        animation.fillAfter = true
        needle.startAnimation(animation)
    }

    fun needleToPause() {
        val animation = AnimationUtils.loadAnimation(this@MusicPlayActivity, R.anim.needle_to_pause)
        animation.fillAfter = true
        needle.startAnimation(animation)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent . KEYCODE_VOLUME_DOWN ,
            KeyEvent.KEYCODE_VOLUME_UP->{
                volumeSeekBar.progress = VolumeUtils.getCurrVolume()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    inner class ViewPagerTouch :View.OnTouchListener{
        var downPoint:Point ? = null
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                when (p1?.action){
                    MotionEvent.ACTION_DOWN->{
                        downPoint = Point()
                        downPoint!!.x = p1.x.toInt()
                        downPoint!!.y = p1.y.toInt()

                    }
                    MotionEvent.ACTION_MOVE->{

                    }
                    MotionEvent.ACTION_UP->{
                        if (Math.abs(p1.x - downPoint!!.x) < 8){
                            if (mainContent.visibility == View.VISIBLE){
                                mainContent.visibility = View.GONE
                                mainContentLyr.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                return false;
        }

    }


    inner class SeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

        var drag = false
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (p0?.id == R.id.volumeSeekBar){
                VolumeUtils.setVolume(p1)
            }else if(p0?.id == R.id.musicSeekBar && drag){
                if (musicPlayService!= null && musicPlayService!!.isPlaying()){
                    musicPlayService!!.setProgress((musicPlayService!!.getDuration()*p1)/Constant.MAXSETPS)
                }
                drag = false
            }


        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            if (p0?.id == R.id.musicSeekBar ){
                drag = true
            }
        }

    }

    inner class PageChanged : ViewPager.OnPageChangeListener {

        var help = -1
        override fun onPageScrollStateChanged(state: Int) {
            help++
            if (help == 0 && isPlaying) {
                needleToPause()
                help = -2
            }
            Log.e("play ", " scrollState")
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            Log.e("play ", " scroll")

        }

        override fun onPageSelected(position: Int) {
            if (isPlaying && musicPlayService != null){
                if (position < musicPlayService!!.getCurrIndex()){
                    controlPlay(Constant.PREVACTION)
                }
                if(position > musicPlayService!!.getCurrIndex()){
                    controlPlay(Constant.NEXTACTION)
                }
            }
            if (isPlaying && needle.rotation < 0) {
                needleToPlay()
            }
            Log.e("play ", " selected")
        }


    }


    /**
     * 这个类原本应该与activity分离的，但是如果分离的话，对于播放列表信息变化的同步
     * 要更耗费资源，而且musicPlayService的Binder是在activity里面
     * 。。。。。虽然这个activity已经非常丑陋。。。。。
     */
    inner class MusicAlbumPageAdapter(): PagerAdapter() {

        init {
            if(albums.size == 0){
                val tempAlbum = AlbumView(this@MusicPlayActivity)
                tempAlbum.setAlbum(null)
                albums.add(tempAlbum)
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (container as ViewPager).removeView(albums.get(position))
        }


        override fun instantiateItem(container: ViewGroup, position: Int): Any {  //这个方法用来实例化页卡
            (container as ViewPager).addView(albums[position])
            return albums[position]
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {     //无限轮播
            return albums.size
        }
    }


}
