package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioSongResponseBean
import com.example.xiaojun.kotlin_try.data.bean.MusicRankResponseBean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
interface MusicRankContact {

    interface Presenter: BasePresenter {
        fun submitData():List<MusicRankResponseBean.MusicRank>
    }

    interface View: BaseView {

    }
}