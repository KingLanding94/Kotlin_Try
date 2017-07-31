package com.example.xiaojun.kotlin_try.presenter

import android.content.Context
import com.example.xiaojun.kotlin_try.contact.MusicSheetContact
import com.example.xiaojun.kotlin_try.data.bean.MusicSheetResponseBean
import com.example.xiaojun.kotlin_try.data.source.remote.MusicSheetDataSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
class MusicSheetPresenter(private val mContext: Context, private val mView: MusicSheetContact.View) : MusicSheetContact.Presenter {

    /**
     * 这个目前有一个bug：当全部数据加载完成以后没有改变底部的view
     */

    val pageSize = 20  //一次加载20个内容
    var currPage = 0
    var sheetList =  ArrayList<MusicSheetResponseBean.MusicSheet>()

    override fun start() {
        val dataSource = MusicSheetDataSource(mContext)
        dataSource.loadSheetList(pageSize, currPage)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(SheetObserver())
    }

    override fun onCompleted() {
        currPage ++
        mView.onSuccess()
    }

    override fun onGoing() {
        mView.onGoing()
    }

    override fun onFailed() {
        mView.onFailed()
    }

    override fun submitData(): List<MusicSheetResponseBean.MusicSheet> {
        return sheetList
    }


    inner class SheetObserver : Observer<MusicSheetResponseBean> {
        private var disposable: Disposable? = null
        override fun onNext(t: MusicSheetResponseBean?) {
            //在服务器返回的json数据里面，还有“音乐人”电台，在此给过滤掉了

            if (t?.content != null)
                sheetList = t?.content as ArrayList<MusicSheetResponseBean.MusicSheet>
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
            disposable!!.dispose()
        }

    }
}