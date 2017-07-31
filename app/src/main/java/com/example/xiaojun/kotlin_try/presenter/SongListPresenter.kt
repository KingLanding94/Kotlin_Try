package com.example.xiaojun.kotlin_try.presenter

import android.content.Context
import com.example.xiaojun.kotlin_try.contact.SongListContact
import com.example.xiaojun.kotlin_try.data.bean.*
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.data.source.remote.MusicRankDataSource
import com.example.xiaojun.kotlin_try.data.source.remote.MusicSheetDataSource
import com.example.xiaojun.kotlin_try.data.source.remote.MusicStationDataSource
import com.example.xiaojun.kotlin_try.util.Constant
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
class SongListPresenter(private val mContext:Context,private val mView:SongListContact.View,
                        private val from:Int,private val identity:String):SongListContact.Presenter {

    private val size:Int = Constant.SONGSIZE //一次加载30首歌曲
    private val page:Int = 0 //从第一页逐渐加载数据

    private var list = ArrayList<SongInfoBean>()
    /**
     * while getting data from local,start() is the same as onGoing()
     */
    override fun start() {
        /**
         * 接下来这段代码会非常的丑，这些本该用接口，然后再产生实例完成具体工作，
         * 但是百度音乐对于歌单内容，电台内容，以及排行榜内容的返回均不相同。
         * 我不知道怎么复用代码把数据整合到一起，所以只有把这些都写出来了。。。
         */

        when(from){
            Constant.FROMRADIO->{
                val musicStationSource = MusicStationDataSource(mContext)
                musicStationSource.loadSongInStation(page,size,identity)
                        ?.map({
                            response:MusicRadioSongResponseBean->
                                responseToSongList(response.result.songlist)
                        })
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(RadioSongObserver())
            }
            Constant.FROMRANK->{
                val musicRankSource = MusicRankDataSource(mContext)
                musicRankSource.loadRankSongList(identity.toInt(),page,size)
                        ?.map({
                            reponse:MusicRankSongResponseBean->
                                responseToSongList(reponse.song_list!!)
                        })
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(RankSongObserver())

            }
            Constant.FROMSHEET->{
                /**
                 * 加载歌单的内容时默认是一次性全部加载完全
                 */
                val musicSheetSource = MusicSheetDataSource(mContext)
                musicSheetSource.loadSheetSongList(identity.toInt())
                        ?.map({
                            response:MusicSheetSongResponseBean->
                                responseToSongList(response.content)
                        })
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(SheetSongObserver())
            }
        }

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

    override fun submitSongs(): List<SongInfoBean> {
        return list
    }

    fun responseToSongList(song_list:List<SongBean.SongInfoForShow>):ArrayList<SongInfoBean>{
        val ret = ArrayList<SongInfoBean>()
        if (song_list == null) return ret
        for (i in song_list!!){
            val song = SongInfoBean()
            song.artistId = i.artist_id
            song.albumId = i.album_id

            if (i.song_id == null)
                song.songId = i.songid
            else
                song.songId = i.song_id

            if (i.artist == null)
                song.artist = i.author
            else
                song.artist = i.artist

            if (i.pic_big != null)
                song.coverLink = i.pic_big
            else
                song.coverLink = i.thumb

            song.album = i.album_title
            song.title = i.title
            ret.add(song)
        }

        return ret
    }

    /**
     * 加载电台里面的歌曲
     */
    inner class RadioSongObserver : Observer<ArrayList<SongInfoBean>> {

        private var disposable: Disposable? = null // 解除订阅关系
        override fun onNext(t: ArrayList<SongInfoBean>?) {
            //在服务器返回的json数据里面，还有“音乐人”电台，在此给过滤掉了
            list = t!!
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

    /**
     * 加载排行榜里面的歌曲
     */
    inner class RankSongObserver : Observer<ArrayList<SongInfoBean>> {

        private var disposable: Disposable? = null // 解除订阅关系
        override fun onNext(t: ArrayList<SongInfoBean>?) {
            //在服务器返回的json数据里面，还有“音乐人”电台，在此给过滤掉了
            list = t!!
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

    /**
     * 加载歌单里面的歌曲
     */
    inner class SheetSongObserver : Observer<ArrayList<SongInfoBean>> {

        private var disposable: Disposable? = null // 解除订阅关系
        override fun onNext(t: ArrayList<SongInfoBean>?) {
            //在服务器返回的json数据里面，还有“音乐人”电台，在此给过滤掉了
            list = t!!
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