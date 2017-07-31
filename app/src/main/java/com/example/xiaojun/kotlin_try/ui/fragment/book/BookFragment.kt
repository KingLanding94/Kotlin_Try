package com.example.xiaojun.kotlin_try.ui.fragment.book

import com.example.xiaojun.kotlin_try.base.BaseFragment
import com.example.xiaojun.kotlin_try.presenter.TypeTabPresenter
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicListFragment
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicRadioFragment
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicRankFragment
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicRecomFragment
import com.example.xiaojun.kotlin_try.util.Constant


class BookFragment : BaseFragment(){


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
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun setFragmentItems() {
        fragments = arrayListOf(MusicRecomFragment(), MusicListFragment(), MusicRankFragment(), MusicRadioFragment())
    }
}
