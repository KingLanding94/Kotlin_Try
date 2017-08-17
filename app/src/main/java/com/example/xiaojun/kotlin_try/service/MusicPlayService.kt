package com.example.xiaojun.kotlin_try.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.*
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.xiaojun.kotlin_try.data.source.remote.MusicPlayDataSource
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.util.Constant
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.content.IntentFilter
import android.os.Handler
import android.widget.Toast
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.listener.MusicPlayListener
import com.example.xiaojun.kotlin_try.util.ToastUtil
import java.lang.Class.*


class MusicPlayService : Service() {

    private val LOOP = 1
    private val RANDOM = 2
    private val SINGLE = 3
    private var playMode = LOOP
    private var currIndex = 0    //当前播放到哪一首音乐
    private var duration = 0    //当前音乐的总时长
    private var currSongInfo:SongInfoBean? = null
    private var currEvent:MusicPlaySys? = null
    private var isPlaying = false
    private val songList = ArrayList<SongInfoBean>()  //音乐播放列表
    private var mMediaPlayer: MediaPlayer? = null
    private val mReceiver = MusicControlReceiver()
    private val mBinder = MediaPlayBinder()
    private val loadDataSource: MusicPlayDataSource = MusicPlayDataSource()
    private var playingListener : MusicPlayListener? = null

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        /**
         * 创建媒体播放器
         */
        if (mMediaPlayer == null) {
            mMediaPlayer = getMediaPlayer(App.getContext())
            mMediaPlayer!!.setOnCompletionListener {
                if (playMode == SINGLE) {
                    play()
                } else {
                    next() //播放完之后切换到下一个
                }
            }
        }
        /**
         * 注册eventBus,目的是设置当前歌单
         */
        EventBus.getDefault().register(this)
        Log.e("service ", "created")

        /**
         * 注册广播
         */
        val filter = IntentFilter()
        filter.addAction(Constant.PLAYCONTROLACTION)
        registerReceiver(mReceiver, filter)

        loadDataSource.setOnLoadDataListener(MLoadDataListener())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.e("service ", "started")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        Log.e("service ", "distroy")
        unregisterReceiver(mReceiver)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayListChange(event: PlayListChangedEvent) {
        Log.e("service ", " msg")
        songList.clear()    //清空原本的播放列表
        songList.addAll(event.songList as ArrayList)
        currIndex = event.mgs as Int

        play()    //播放所点击的音乐
    }

    /**
     * 当前的主要问题是：播放的时候怎么把歌单传递给
     */
    fun pause() {
        val intent = Intent()
        intent.action = Constant.UPDATEVIEWACTION
        intent.putExtra("what",Constant.PAUSEACTION)
        sendBroadcast(intent)

        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) {
            mMediaPlayer!!.pause()
        }
        isPlaying = false
        Log.e("service ", "pause")
    }

    /**
     * 网络的可用性检测在activity里面实现，如果网络不可用，那么特定音乐不可以点开
     */
    fun play() {
        try {
            loadDataSource.loadSongInfo(App.getContext(), songList[currIndex])
        }catch (e:IndexOutOfBoundsException){
            ToastUtil.shortShow("当前没有选中任何歌曲")
        }

    }

    fun next() {
        if (playingListener != null){
            playingListener?.onSongChanged()
        }
        when (playMode) {
            LOOP -> {
                currIndex++
                if (currIndex == songList.size) {
                    currIndex = 0
                }
                play()
            }
            RANDOM -> {
                currIndex = (Math.random() * (songList.size - 1)).toInt()
                play()
            }
        }
        Log.e("service ", "next")
    }

    fun prev() {
        if (playingListener != null){
            playingListener?.onSongChanged()
        }
        when (playMode) {
            LOOP,
            SINGLE -> {
                currIndex--
                if (currIndex < 0) {
                    currIndex = songList.size - 1
                }
                play()
            }
            RANDOM -> {
                currIndex = (Math.random() * (songList.size - 1)).toInt()
                play()
            }
        }
        Log.e("service ", "prev")
    }

    fun setPlayMode(mode:Int) {
        playMode = mode
    }

    /**
     * 为了网络播放音乐，音乐网络播放音乐不能一次性的得到全部播放路径
     */
    fun addSongToList(song: SongInfoBean) {
        songList += song
    }

    /**
     * 切换播放列表，主要用于本地音乐播放列表的设定
     */
    fun setSongList(list: ArrayList<SongInfoBean>) {
        songList.clear()
        songList += list
    }

    fun getSongList():ArrayList<SongInfoBean>{
        return songList
    }

    fun isPlaying():Boolean{
        return isPlaying
    }

    fun getCurrIndex():Int{
        return currIndex
    }

    /**
     * 得到当前音乐的总时长
     */
    fun getDuration(): Int {
        return duration
    }

    fun getCurrDuration():Int{
         return mMediaPlayer?.currentPosition ?:0
    }

    /**
     * 得到当前音乐的播放进度
     */
    fun getProgress(): Int {
        return mMediaPlayer!!.currentPosition
    }

    /**
     * @progress单位是毫秒
     */
    fun setProgress(progress: Int) {
        if (mMediaPlayer == null) {
            throw IllegalStateException("mediaPlayer has not bean started!")
        } else {
            mMediaPlayer!!.seekTo(progress)
        }
    }

    fun setPlayingListener(listener: MusicPlayListener){
        playingListener = listener
    }


    /**
     *作用是做类似于下面隐藏API的工作,mediaplayer播放音乐时老是会有一个报错，虽然不影响使用，但是下面是解决方案
     */
    private fun getMediaPlayer(context: Context): MediaPlayer {

        val mediaplayer = MediaPlayer()

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer
        }

        try {
            @SuppressLint("PrivateApi")
            val cMediaTimeProvider = forName("android.media.MediaTimeProvider")
            @SuppressLint("PrivateApi")
            val cSubtitleController = forName("android.media.SubtitleController")
            @SuppressLint("PrivateApi")
            val iSubtitleControllerAnchor = forName("android.media.SubtitleController\$Anchor")
            @SuppressLint("PrivateApi")
            val iSubtitleControllerListener = forName("android.media.SubtitleController\$Listener")

            val constructor = cSubtitleController.getConstructor(*arrayOf(Context::class.java, cMediaTimeProvider, iSubtitleControllerListener))

            val subtitleInstance = constructor.newInstance(context, null, null)

            val f = cSubtitleController.getDeclaredField("mHandler")

            f.isAccessible = true
            try {
                f.set(subtitleInstance, Handler())
            } catch (e: IllegalAccessException) {
                return mediaplayer
            } finally {
                f.isAccessible = false
            }

            val setsubtitleanchor = mediaplayer.javaClass.getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor)

            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null)
        } catch (e: Exception) {
        }

        return mediaplayer
    }

    /**
     * 目前是把BroadcastReciver放到播放服务里了，直接通过inner class的特性来访问服务内部变量和函数
     */
    inner class MusicControlReceiver() : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val WHAT = intent.extras.getInt("what")
            when (WHAT) {
                Constant.REQUESTINFO->{
                    var playInfo:MusicPlaySys.PlayInfo? = null
                    if (isPlaying){
                        currSongInfo = songList[currIndex]
                        currSongInfo?.duration = duration
                        playInfo = MusicPlaySys.PlayInfo(true,mMediaPlayer?.currentPosition!!)
                    }else{
                        if (songList.size >0){
                            currSongInfo = songList[currIndex]
                            currSongInfo?.duration = duration
                            playInfo = MusicPlaySys.PlayInfo(false,0)
                        }else{
                            currSongInfo = SongInfoBean()
                            currSongInfo?.duration = duration
                            playInfo = MusicPlaySys.PlayInfo(false,0)
                        }
                    }
                    currEvent = MusicPlaySys(currSongInfo!!,playInfo)
                    EventBus.getDefault().post(currEvent)
                }
                Constant.PLAYACTION -> {
                    play()
                }
                Constant.PAUSEACTION -> {
                    pause()
                }
                Constant.PREVACTION -> {
                    prev()
                }
                Constant.NEXTACTION -> {
                    next()
                }
                Constant.ISPLAYING->{
                    EventBus.getDefault().post(MusicPlayingStateEvent(isPlaying))
                }
                in (Constant.MODEACTION..Constant.MODEACTION+Constant.MODE_RANDOM)-> {
                    setPlayMode(WHAT%Constant.MODEACTION)
                }
            }
        }
    }

    inner open class MediaPlayBinder : Binder() {
        fun getMediaPlayService(): MusicPlayService {
            return this@MusicPlayService
        }
    }


    inner class MyReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Log.e("get ", "message")
        }
    }

    inner class MLoadDataListener : MusicPlayDataSource.OnLoadDataListener {

        override fun OnFailed() {
            Toast.makeText(App.getContext(), "音乐资源加载失败", Toast.LENGTH_SHORT).show()
        }

        override fun OnSuccess() {
            val intent = Intent()
            //更改悬浮按钮的状态
            intent.action = Constant.UPDATEVIEWACTION
            intent.putExtra("what",Constant.PLAYACTION)
            sendBroadcast(intent)


            /**
             * 加载成功，播放音乐
             */
            currSongInfo = loadDataSource.getSongInfo()
            var sourceUri = ""
            if (currSongInfo?.from == Constant.FROMNET) {
                sourceUri += currSongInfo?.songLink
            } else {
                sourceUri += currSongInfo?.songPath
            }
            try {
                mMediaPlayer!!.reset()
                mMediaPlayer!!.setDataSource(sourceUri)
                mMediaPlayer!!.prepareAsync()
                mMediaPlayer!!.setOnPreparedListener({
                    duration = mMediaPlayer!!.duration
                    mMediaPlayer!!.start()
                    isPlaying = true
                    currSongInfo!!.duration = duration
                    val playInfo = MusicPlaySys.PlayInfo(true,0)
                    currEvent = MusicPlaySys(currSongInfo!!,playInfo)
                    EventBus.getDefault().post(currEvent)//把完整的信息再给musicPlayActivity发送一次

            })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}
