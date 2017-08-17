package com.example.xiaojun.kotlin_try.ui.fragment.book

import com.example.xiaojun.kotlin_try.base.BaseFragmentForMain
import com.example.xiaojun.kotlin_try.presenter.TypeTabPresenter
import com.example.xiaojun.kotlin_try.util.Constant


class BookFragment : BaseFragmentForMain(){


    var mPresenter  = TypeTabPresenter(Constant.BOOK,this)

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


    }

    override fun setFragmentItems() {
        fragments = arrayListOf(PlaceHolderFragment(),PlaceHolderFragment(),PlaceHolderFragment(),PlaceHolderFragment())
    }
}
