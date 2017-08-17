package com.example.xiaojun.kotlin_try.ui.fragment.movie

import android.support.v4.app.Fragment
import com.example.xiaojun.kotlin_try.base.BaseFragmentForMain
import com.example.xiaojun.kotlin_try.presenter.TypeTabPresenter
import com.example.xiaojun.kotlin_try.util.Constant

/**
 * Created by Xiaojun on 2017/7/15.
 */

/**
 * movieFragment是相对于整个项目而言的，movie，music，book
 * 这个fragment里面设置tab是用rxjava，原本认为标题可能是由服务器发送到客户端，然后再显示
 */
class MovieFragment:BaseFragmentForMain() {

    var mPresenter  = TypeTabPresenter(Constant.MOVIE,this)

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

        fragments.add(MovieTypeFragment.newInstance(Constant.NET,Constant.THEATER))
        fragments.add(MovieTypeFragment.newInstance(Constant.NET,Constant.COMING))
        fragments.add(MovieTypeFragment.newInstance(Constant.NET,Constant.Top250))
        fragments.add(MovieLocalFragment() as Fragment)
    }
}