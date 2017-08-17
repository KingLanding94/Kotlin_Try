package com.example.xiaojun.kotlin_try.ui.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.xiaojun.kotlin_try.adapter.MovieLocalAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForList
import com.example.xiaojun.kotlin_try.contact.MovieLocalContact
import com.example.xiaojun.kotlin_try.data.bean.MovieBean
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MovieLocalPresenter
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.ui.activity.movie.MoviePlayActivity
import com.example.xiaojun.kotlin_try.util.ToastUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
class MovieLocalFragment:BaseFragmentForList(),MovieLocalContact.View {

    val mPresenter = MovieLocalPresenter(this)
    var list = ArrayList<MovieBean.Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(){
        super.initView()
        recyclerView!!.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        recyclerView!!.addItemDecoration(RecyclerViewItemSpace(30,0,0))
    }

    override fun loadData() {
        super.loadData()
        mPresenter.start()
    }

    override fun onSuccess() {
        super.onSuccess()
        Log.e("success","movieLocal")
        list = mPresenter.submitData()
        var mAdapter = MovieLocalAdapter(formatData())
        mAdapter.clickListener = object : MOnRecyclerViewClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(activity,MoviePlayActivity::class.java)
                intent.putExtra("video",list[position])
                startActivity(intent)
            }
        }
        recyclerView!!.adapter = mAdapter
    }

    fun formatData():ArrayList<HashMap<String,Any>>{
        val ret = ArrayList<HashMap<String,Any>>()
        for (i in list){
            val map = HashMap<String,Any>()
            map.put("title",i.title!!)
            map.put("duration",i.duration!!)
            map.put("thumb",i.thumbPath!!)
            map.put("detail",i.size!! + "")
            ret.add(map)
        }
        return ret
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        super.onRefresh(refreshlayout)
        ToastUtil.longShow("已经加载全部视频")
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        super.onLoadmore(refreshlayout)
        ToastUtil.longShow("已经加载全部视频")
    }
}