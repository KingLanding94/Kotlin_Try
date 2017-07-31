package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView

/**
 * Created by Xiaojun on 2017/7/17.
 */
interface TypeTabContact {

    /**
     * This is where movie music or book set tabs
     */
    interface View : BaseView{

    }

    interface Presenter: BasePresenter{
        fun submitTabItems():ArrayList<String>
    }
}