package com.example.xiaojun.kotlin_try.contact

import com.example.xiaojun.kotlin_try.base.BasePresenter
import com.example.xiaojun.kotlin_try.base.BaseView
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.data.bean.SongBean
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
interface MusicLocalContract {
    interface Presenter: BasePresenter {
        fun submitData():ArrayList<SongInfoBean>
    }

    interface View: BaseView {

    }
}