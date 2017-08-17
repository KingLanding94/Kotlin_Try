package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MusicSongListAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForAll
import com.example.xiaojun.kotlin_try.contact.MusicLocalContract
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MusicLocalPresenter
import com.example.xiaojun.kotlin_try.service.PlayListChangedEvent
import com.example.xiaojun.kotlin_try.ui.activity.music.MusicPlayActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MusicLocalFragment: BaseFragmentForAll(),MusicLocalContract.View,View.OnClickListener{

    val OPEN = true
    val CLOSED = false
    private val mPresenter = MusicLocalPresenter(this)
    private var recyclerView:RecyclerView? = null
    private var songList = ArrayList<SongInfoBean>()
    private var capacity:TextView? = null
    private var mutiChoose:LinearLayout? = null
    private var playAll:RelativeLayout? = null
    private var sheetCollectedState = CLOSED
    private var sheetCreatedState = CLOSED

    override fun initView() {
        super.initView()
        recyclerView = mView!!.findViewById(R.id.songLocalRecyclerView)
        capacity = mView!!.findViewById(R.id.songListCapacity)
        mutiChoose = mView!!.findViewById(R.id.mutiChoose)
        playAll = mView!!.findViewById(R.id.playAll)

        mutiChoose?.setOnClickListener(this)
        playAll?.setOnClickListener(this)

        val layoutManager =  LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        layoutManager.isSmoothScrollbarEnabled = true
        layoutManager.isAutoMeasureEnabled = true
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.addItemDecoration(RecyclerViewItemSpace(2,20,0))
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.isNestedScrollingEnabled = false
    }

    override fun loadData() {
        super.loadData()
        mPresenter.start()

    }
    override fun getContentView(): Int {
        return R.layout.fragment_music_local
    }

    override fun onSuccess() {
        super.onSuccess()
        songList = mPresenter.submitData()
        capacity?.text = songList.size.toString()
        val adapter = MusicSongListAdapter(activity, formatData())
        adapter.setOnCLickListener(object : MOnRecyclerViewClickListener {
            override fun onClick(view: View, position: Int) {
                val event = PlayListChangedEvent(songList, position)
                EventBus.getDefault().post(event)
                val intent = Intent(activity, MusicPlayActivity::class.java)
                startActivity(intent)
            }
        })

        recyclerView!!.adapter = adapter

        Log.e("localMusic","success"+ songList.size)
    }

    override fun onFailed() {
        super.onFailed()
        Log.e("musicLocal"," failed")
    }

    //点击音乐的时候是要一次性把歌单全部传给playService吗
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.mutiChoose->{
                //跳转到多选界面
            }
            R.id.playAll->{
                //播放全部
            }
        }
    }

    fun formatData():ArrayList<HashMap<String,Any>>{
        val mapList = ArrayList<HashMap<String,Any>>()
        for (i in songList){
            val map = HashMap<String,Any>()
            map.put("order",MusicSongListAdapter.HIDE)
            map.put("title", i.title)
            var detail = ""
            detail = i.artist+" - "+ i.album
            map.put("detail",detail)
            mapList.add(map)
        }
        return mapList
    }
}