package com.example.xiaojun.kotlin_try.ui.activity.music

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MusicSongListAdapter
import com.example.xiaojun.kotlin_try.contact.SongListContact
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.service.PlayListChangedEvent
import com.example.xiaojun.kotlin_try.presenter.SongListPresenter
import com.example.xiaojun.kotlin_try.service.MusicPlayService
import com.example.xiaojun.kotlin_try.util.Constant
import kotlinx.android.synthetic.main.activity_song_sheet.*
import kotlinx.android.synthetic.main.song_list_operation.*
import kotlinx.android.synthetic.main.song_list_play.*
import org.greenrobot.eventbus.EventBus
import android.os.Build



/**
 * 这个activity需要展示来自歌单以及电台的数据
 */
class SongSheetActivity : AppCompatActivity(),SongListContact.View {

    private var mPresenter:SongListContact.Presenter? = null
    private var songList:ArrayList<SongInfoBean>? = null
    private var adapter:MusicSongListAdapter? = null
    private var isOver:Boolean = false // 判断是否还可以继续加载，是否已经加载完全
    private var efficientCount = Constant.SONGSIZE  //这个数据是取决于请求的数据发送的大小

    /**
     * 下面是标示有关的数据
     */
    private var from = 0
    private var identity:String? = null
    private var cover: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_sheet)

        getParamAndSet()
        mPresenter = SongListPresenter(this,this,from,identity!!)
        initView()
    }

    fun getParamAndSet(){
        val extras = intent.extras
        from = extras.getInt("from")
        identity = extras.getString("identity")
        cover = extras.getString("cover")
        Glide.with(this).load(cover).error(R.drawable.temp).centerCrop().into(songListBg)
        Glide.with(this).load(cover).error(R.drawable.temp).centerCrop().into(songListCover)
    }

    fun initView(){
        mPresenter!!.start()
        setSupportActionBar(songSheetToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        songSheetToolbar?.setNavigationOnClickListener({
            onBackPressed()
        })

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        songSheetCollTool?.title = getString(R.string.music_song_sheet)
        //通过CollapsingToolbarLayout修改字体颜色
        songSheetCollTool?.setExpandedTitleColor(Color.TRANSPARENT)//设置还没收缩时状态下字体颜色
        songSheetCollTool?.setCollapsedTitleTextColor(Color.WHITE)//设置收缩后Toolbar上字体的颜色

        //设置RecyclerView的显示方式
        val layoutManager:LinearLayoutManager = LinearLayoutManager(this)
        layoutManager.setSmoothScrollbarEnabled(true)
        layoutManager.setAutoMeasureEnabled(true)
        songListRecyclerView.layoutManager = layoutManager
        songListRecyclerView.setHasFixedSize(true)
        songListRecyclerView.isNestedScrollingEnabled = false

    }

    /**
     * 接Presenter注释，由于百度音乐返回数据不同的原因，数据项里面有些是空的，因为同样的属性具有不同的名称
     */
    fun formatData():List<HashMap<String,Any>>{
        val mapList = ArrayList<HashMap<String,Any>>()
        for (i in 0..efficientCount-1){
            val map = HashMap<String,Any>()
            map.put("order",i+1)
            map.put("title",songList!![i].title as Any)
            var detail = ""
            detail = songList!![i].artist
            if (songList!![i].album != null){
                detail += " - " + songList!![i].album
            }
            map.put("detail",detail)
            mapList.add(map)
        }
        return mapList
    }

    override fun onSuccess() {

        songList = mPresenter!!.submitSongs() as ArrayList<SongInfoBean>
        //对songlist的无效元素进行处理
        if (efficientCount > songList!!.size){
            efficientCount = songList!!.size
        }
        for (i in 0..songList!!.size-1){
            if (songList!![i].title == null){
                efficientCount = i
                break
            }
        }
        //内容已经全部加载完全
        if (efficientCount < Constant.SONGSIZE){
            isOver = true
        }
        songListCapacity.text = "(共"+efficientCount+"首)"
        adapter = MusicSongListAdapter(this,formatData())
        adapter!!.clickListener = SongClickListener()
        songListRecyclerView.adapter = adapter
    }

    override fun onFailed() {
        //
    }

    override fun onGoing() {
        //
    }

    inner class SongClickListener : MOnRecyclerViewClickListener {

        /**
         * 先根据歌曲id获取歌曲的可播放信息
         */
        override fun onClick(view: View, position: Int) {
            val event = PlayListChangedEvent(songList, position)
            EventBus.getDefault().post(event)
            val intent = Intent(this@SongSheetActivity, MusicPlayActivity::class.java)
            startActivity(intent)
        }

    }



}
