package com.example.xiaojun.kotlin_try.presenter

import android.util.Log
import com.example.xiaojun.kotlin_try.contact.MovieTypeContact
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.data.source.remote.MovieListDataSource
import com.example.xiaojun.kotlin_try.util.Constant
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MovieListPresenter(private val mView:MovieTypeContact.View,private val id:String):MovieTypeContact.Presenter {



    /**
     * 豆瓣返回的数据是默认每次20条，所以每次请求之后都把offset+20
     */
    var theaterOffset = 0
    var top250Offset = 0
    var comingOffset = 0
    val stepSize = 20
    var response:MovieDoubanResponseBean? = null

    val REFRESH = -1
    val LOADMORE = 1

    override fun start() {
        val movieDataSource = MovieListDataSource()
        var observable: Observable<MovieDoubanResponseBean>? = null
        when(id){
            Constant.THEATER->{
                observable = movieDataSource.loadTheaterMovie(theaterOffset)
                theaterOffset += stepSize
            }
            Constant.Top250->{
                observable = movieDataSource.loadTopgMovie(top250Offset)
                top250Offset += stepSize
            }
            Constant.COMING->{
                observable = movieDataSource.loadComingMovie(comingOffset)
                comingOffset += stepSize
            }
        }
        observable?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(MovieListObserver())
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

    override fun submitData(): MovieDoubanResponseBean {
       return response!!
    }

    inner class MovieListObserver : Observer<MovieDoubanResponseBean> {
        private var disposable: Disposable? = null
        override fun onNext(t: MovieDoubanResponseBean?) {
            response = t
        }

        override fun onComplete() {
            onCompleted()
            disposable!!.dispose()
        }

        override fun onSubscribe(d: Disposable?) {
            disposable = d

        }

        override fun onError(e: Throwable?) {
            onFailed()
            Log.e("error","presenter"+e.toString())
            disposable!!.dispose()
        }

    }


}