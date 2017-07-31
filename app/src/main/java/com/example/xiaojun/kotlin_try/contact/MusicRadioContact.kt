package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioStationResponseBean

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */
interface MusicRadioContact {

    interface Presenter: BasePresenter {
        fun submitData():MutableList<MusicRadioStationResponseBean.MusicRadioStation>
    }

    interface View: BaseView {

    }
}