package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView

/**
 * Created by XiaoJun on 2017/7/17.
 * Version 1.0.0
 */
interface MusicListContact {

    interface Presenter: BasePresenter{
        fun submitData():List<HashMap<String,Any>>
    }

    interface View: BaseView{

    }

}