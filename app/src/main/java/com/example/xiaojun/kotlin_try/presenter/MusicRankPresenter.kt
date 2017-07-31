package com.example.xiaojun.kotlin_try.presenter

import android.content.Context
import com.example.xiaojun.kotlin_try.contact.MusicRankContact
import com.example.xiaojun.kotlin_try.data.bean.MusicRankResponseBean
import com.example.xiaojun.kotlin_try.data.source.remote.MusicRankDataSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
class MusicRankPresenter(private val mContext:Context,private val mView:MusicRankContact.View):MusicRankContact.Presenter {

    var list:List<MusicRankResponseBean.MusicRank>? = null

    override fun start() {
        val dataSource = MusicRankDataSource(mContext)
        dataSource.loadRankList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(RankObserver())

    }

    override fun onCompleted() {
        mView.onSuccess()
    }

    override fun onGoing() {
        mView.onGoing()
    }

    override fun onFailed() {
        mView.onFailed()
    }

    override fun submitData(): List<MusicRankResponseBean.MusicRank> {
        return list!!
    }

    inner class RankObserver : Observer<MusicRankResponseBean>{
        private var disposable:Disposable? = null

        override fun onSubscribe(d: Disposable?) {
            disposable = d
        }

        override fun onComplete() {
            onCompleted()
            disposable?.dispose()
        }

        override fun onNext(t: MusicRankResponseBean?) {
            list = t?.content
        }

        override fun onError(e: Throwable?) {
            onFailed()
            disposable?.dispose()
        }

    }
}