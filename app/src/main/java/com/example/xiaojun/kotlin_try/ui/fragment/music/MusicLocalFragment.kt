package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.base.BaseFragmentForAll
import com.example.xiaojun.kotlin_try.contact.MusicLocalContract
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.mlibrary.MRecyclerView
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MusicLocalPresenter
import com.example.xiaojun.kotlin_try.service.PlayListChangedEvent
import com.example.xiaojun.kotlin_try.ui.activity.music.MusicPlayActivity
import com.example.xiaojun.kotlin_try.util.ToastUtil
import org.greenrobot.eventbus.EventBus

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MusicLocalFragment: BaseFragmentForAll(),MusicLocalContract.View,View.OnClickListener{

    val OPEN = true
    val CLOSED = false
    private val mPresenter = MusicLocalPresenter(this)
    private var recyclerView: MRecyclerView? = null
    private var songList = ArrayList<SongInfoBean>()

    override fun initView() {
        super.initView()
        recyclerView = mView!!.findViewById(R.id.songLocalRecyclerView)

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

        /**
         * MRecyclerView的具体使用。从下面这么多代码看来可能会觉得MRecyclerView很难使用
         * 实际上并不是，下面设置了很多其实只是为了演示接口的用法
         */

        recyclerView?.setDataSize(songList.size)
        recyclerView?.setBindDataServer(object :MRecyclerView.BindDataService{  //设置具体的绑定
            @SuppressLint("SetTextI18n")
            override fun OnBindData(holder: MRecyclerView.MViewHolder?, position: Int, type:Int) {
                when (type){
                    MRecyclerView.NORMAL->{
                        (holder?.getView(R.id.songOrder) as TextView).text = position.toString()
                        (holder.getView(R.id.songTitle) as TextView).text = songList[position].title
                        var detail = ""
                        detail = songList[position].artist+" - "+ songList[position].album
                        (holder.getView(R.id.songDetail) as TextView).text = detail
                        //为歌曲条目的更多设置监听
                        (holder.getView(R.id.songOperatorMore) as ImageView).setOnClickListener(this@MusicLocalFragment)
                    }
                    MRecyclerView.HEADER->{
                        (holder?.getView(R.id.songListCapacity) as TextView).text = "共("+songList.size.toString()+")首"
                        (holder.getView(R.id.mutiChoose) as LinearLayout).setOnClickListener(this@MusicLocalFragment)
                        (holder.getView(R.id.songListPlayIcon) as ImageView).setOnClickListener(this@MusicLocalFragment)
                    }

                    //这个里面没有footer，所以就不设置了
                    MRecyclerView.FOOTER->{

                    }
                }
            }
        })
        recyclerView?.setOnItemClickedListener(object :MRecyclerView.OnItemClickedListener{

            override fun onClick(view: View?) {

            }
            override fun onClick(position: Int) {
                val event = PlayListChangedEvent(songList, position)
                EventBus.getDefault().post(event)
                val intent = Intent(activity, MusicPlayActivity::class.java)
                startActivity(intent)
            }
        })
        recyclerView?.show()
        Log.e("localMusic","success"+ songList.size)
    }

    override fun onFailed() {
        super.onFailed()
        Log.e("musicLocal"," failed")
    }

    override fun onClick(p0: View?) {

        when (p0?.id){
            R.id.mutiChoose->{
                ToastUtil.shortShow("you clicked multiChoose!")
            }
            R.id.songListPlayIcon->{
                ToastUtil.shortShow("we will play all of the music for you!")
            }
            R.id.songOperatorMore->{
                ToastUtil.shortShow("Operator More!")
            }
        }
    }
}