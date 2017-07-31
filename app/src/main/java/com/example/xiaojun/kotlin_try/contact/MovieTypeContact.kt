package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
interface MovieTypeContact {
    interface Presenter: BasePresenter {
        fun submitData():MovieDoubanResponseBean
    }

    interface View: BaseView {

    }
}