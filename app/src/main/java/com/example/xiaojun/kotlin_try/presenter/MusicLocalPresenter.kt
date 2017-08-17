package com.example.xiaojun.kotlin_try.presenter

import android.util.Log
import com.example.xiaojun.kotlin_try.contact.MusicLocalContract
import com.example.xiaojun.kotlin_try.data.bean.SongBean
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.data.source.local.MusicLocalSource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
class MusicLocalPresenter(val mView:MusicLocalContract.View):MusicLocalContract.Presenter {

    private val localMusicDataSource = MusicLocalSource()
    private val list = ArrayList<SongInfoBean>()


    //目前获取歌曲的方式还是通过ContentProvider

    override fun start() {
        Observable.create({
            e: ObservableEmitter<MutableList<SongInfoBean>>? ->
            e?.onNext(localMusicDataSource.getMusicList())
            e?.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<MutableList<SongInfoBean>> {
                    var disposable: Disposable? = null
                    override fun onNext(t: MutableList<SongInfoBean>?) {
                        list.addAll(t!!)
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable = d
                    }

                    override fun onComplete() {
                        disposable?.dispose()
                        onCompleted()
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("loadDate",e.toString())
                        disposable?.dispose()
                        onFailed()
                    }

                })
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

    override fun submitData(): ArrayList<SongInfoBean> {
        return list
    }
}