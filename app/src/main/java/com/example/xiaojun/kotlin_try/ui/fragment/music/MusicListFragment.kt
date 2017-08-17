package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MusicListOfMenuAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForAll
import com.example.xiaojun.kotlin_try.contact.MusicListContact
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.presenter.MusicListPresenter
import com.example.xiaojun.kotlin_try.ui.activity.music.LocalSongSheetActivity


class MusicListFragment : BaseFragmentForAll(),MusicListContact.View {
    /**
     * 如果用户并没有打开过这个页面，那么这个页面一直都不会被加载
     */

    private val mPresenter = MusicListPresenter(this)
    private var mData: List<HashMap<String,Any>>? = null
    private var menuList: RecyclerView? = null

    override fun getContentView(): Int {
        return R.layout.fragment_music_list
    }


    override fun loadData() {
        super.loadData()
        mPresenter.start()
    }

    override fun initView(){
        menuList = mView!!.findViewById(R.id.musicListRecyclerView)
        menuList!!.layoutManager = LinearLayoutManager(activity)
    }

    override fun onSuccess() {
        super.onSuccess()
        mData = mPresenter.submitData()
        val adapter =  MusicListOfMenuAdapter(activity,mData!!)
        adapter.clickListener = RecyclerViewClick()
        menuList!!.adapter = adapter

    }
    /**
     * set item click listener
     */
    inner class RecyclerViewClick : MOnRecyclerViewClickListener {
        override fun onClick(view: View, position: Int) {
            when(position){

            }
            startActivity(Intent(this@MusicListFragment.activity, LocalSongSheetActivity::class.java))
        }

    }

}
