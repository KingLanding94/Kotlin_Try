package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
interface SongListContact {

    interface Presenter: BasePresenter {
        fun submitSongs():List<SongInfoBean>
    }

    interface View: BaseView {

    }
}