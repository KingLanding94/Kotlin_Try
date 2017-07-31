package com.example.xiaojun.kotlin_try.presenter

import android.content.Context
import com.example.xiaojun.kotlin_try.contact.MusicRadioContact
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioStationResponseBean
import com.example.xiaojun.kotlin_try.data.source.remote.MusicStationDataSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */
class MusicRadioPresenter(val context: Context,val view:MusicRadioContact.View):MusicRadioContact.Presenter {

    private var stationList = ArrayList<MusicRadioStationResponseBean.MusicRadioStation>()
    private val musicStationSource = MusicStationDataSource(context)


    override fun start() {
        musicStationSource.loadData(context)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(RadioObserver())
    }

    override fun onCompleted() {
        view.onSuccess()
    }

    override fun onGoing() {
        view.onGoing()
    }

    override fun onFailed() {
        view.onFailed()
    }

    override fun submitData(): MutableList<MusicRadioStationResponseBean.MusicRadioStation> {
        return stationList
    }

    inner class RadioObserver : Observer<MusicRadioStationResponseBean>{
        private var disposable:Disposable? = null
        override fun onNext(t: MusicRadioStationResponseBean ?) {
            //在服务器返回的json数据里面，还有“音乐人”电台，在此给过滤掉了
            if (t?.result != null && t.result.isNotEmpty()){
                stationList = t.result[0].channellist as ArrayList<MusicRadioStationResponseBean.MusicRadioStation>
            }
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