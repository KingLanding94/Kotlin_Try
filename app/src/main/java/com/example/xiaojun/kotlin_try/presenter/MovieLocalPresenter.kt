package com.example.xiaojun.kotlin_try.presenter

import com.example.xiaojun.kotlin_try.contact.MovieLocalContact
import com.example.xiaojun.kotlin_try.data.bean.MovieBean
import com.example.xiaojun.kotlin_try.data.source.local.MovieDataSource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
class MovieLocalPresenter(val mView: MovieLocalContact.View) : MovieLocalContact.Presenter {

    var list = ArrayList<MovieBean.Video>()
    val localMovieDataSource = MovieDataSource()

    override fun start() {
        Observable.create({
            e: ObservableEmitter<MutableList<MovieBean.Video>>? ->
            e?.onNext(localMovieDataSource.getList()!!)
            e?.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<MutableList<MovieBean.Video>> {
                    var disposable: Disposable? = null
                    override fun onNext(t: MutableList<MovieBean.Video>?) {
                        list = t as ArrayList<MovieBean.Video>
                        onGoing()
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable = d
                    }

                    override fun onComplete() {
                        disposable?.dispose()
                        onCompleted()
                    }

                    override fun onError(e: Throwable?) {
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

    override fun submitData(): ArrayList<MovieBean.Video> {
        return list
    }

}