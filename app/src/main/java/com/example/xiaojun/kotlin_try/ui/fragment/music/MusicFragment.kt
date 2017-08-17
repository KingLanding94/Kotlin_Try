package com.example.xiaojun.kotlin_try.ui.fragment.music

import com.example.xiaojun.kotlin_try.base.BaseFragmentForMain
import com.example.xiaojun.kotlin_try.presenter.TypeTabPresenter
import com.example.xiaojun.kotlin_try.util.Constant

/**
* Created by XiaoJun on 2017/7/14.
* Version 1.0.0
*/
class MusicFragment:BaseFragmentForMain() {


    var mPresenter  = TypeTabPresenter(Constant.MUSIC,this)

    override fun initView() {
        mPresenter.start()
    }

    override fun onSuccess() {
        tabs = mPresenter.submitTabItems()
    }

    override fun onFailed() {
        //nothing
    }

    override fun onGoing() {
        //nothing
    }

    override fun setFragmentItems() {
        fragments = arrayListOf(MusicRecomFragment(), MusicListFragment(), MusicRankFragment(), MusicRadioFragment())
    }


}