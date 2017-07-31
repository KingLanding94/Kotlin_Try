package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.bean.MusicRankResponseBean
import com.example.xiaojun.kotlin_try.data.bean.MusicSheetResponseBean

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
interface MusicSheetContact {

    interface Presenter: BasePresenter {
        fun submitData():List<MusicSheetResponseBean.MusicSheet>
    }

    interface View: BaseView {

    }
}