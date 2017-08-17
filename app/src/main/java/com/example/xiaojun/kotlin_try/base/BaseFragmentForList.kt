package com.example.xiaojun.kotlin_try.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Created by XiaoJun on 2017/7/28.
 * Version 1.0.0
 */
open abstract class BaseFragmentForList :BaseFragmentForAll(),OnRefreshListener,OnLoadmoreListener{

    protected var recyclerView:RecyclerView? = null
    protected var refreshLayout:RefreshLayout? = null
//    protected var isFirst = true;

    override fun initView() {
        super.initView()
        recyclerView = root!!.findViewById(R.id.showContentRecyclerView)
        refreshLayout = root!!.findViewById<SmartRefreshLayout>(R.id.refreshLayout)
        refreshLayout!!.setOnRefreshListener(this)
    }

    override fun getContentView():Int {
        return R.layout.fragment_base_for_list
    }

    override fun onSuccess() {
        super.onSuccess()
        showSuccess()
    }

    override fun onFailed() {
        super.onSuccess()
        showFailed()
    }

    override fun onGoing() {
        super.onGoing()
    }

    fun showFailed(){
        if (refreshLayout == null) return
        if(refreshLayout!!.isRefreshing){
            refreshLayout!!.finishRefresh()
        }
        if (refreshLayout!!.isLoading){
            refreshLayout!!.finishLoadmore()
        }
    }

    fun showSuccess(){
        if (refreshLayout == null) return
        if(refreshLayout!!.isRefreshing){
            refreshLayout!!.finishRefresh()
        }
        if (refreshLayout!!.isLoading){
            refreshLayout!!.finishLoadmore()
        }
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishLoadmore(2000)   //默认2000mill结束动画
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh(2000)  //默认2000mill结束动画
    }

}