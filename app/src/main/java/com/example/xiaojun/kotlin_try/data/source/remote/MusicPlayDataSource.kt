package com.example.xiaojun.kotlin_try.data.source.remote

import android.content.Context
import com.example.xiaojun.kotlin_try.data.bean.MusicPlayResponseBean
import com.example.xiaojun.kotlin_try.data.bean.SongBean
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.service.BaiduMusicApi
import com.example.xiaojun.kotlin_try.mlibrary.RetrofitClient
import com.example.xiaojun.kotlin_try.util.Constant
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by XiaoJun on 2017/7/24.
 * Version 1.0.0
 */
class MusicPlayDataSource {

    /**
     * 现在这种写法需要将网络加载和本地加载融合在一起，这样的架构才会更好一些
     */

    private var songInfo:SongInfoBean? = null
    private var songLrc:SongBean.SongLrc? = null
    private var listener: OnLoadDataListener? = null


    fun loadLyr(mContext: Context, songId: Int): SongBean.SongLrc? {
        var lyr: SongBean.SongLrc? = null
        val retrofit = RetrofitClient.getInstance(mContext, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        apiService?.getSongLry(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format, "baidu.ting.song.lry", songId)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({
                    songLyr: SongBean.SongLrc ->
                    lyr = songLyr
                })
        return lyr     //出错的情况返回为空，所以在使用的时候还要注意一下
    }

    /**
     * 根据id加载歌曲的信息,现在的问题是获得到的可播放信息是网络获取，
     */
    fun loadSongInfo(mContext: Context, song:SongInfoBean) {
        val retrofit = RetrofitClient.getInstance(mContext, BaiduMusicApi.MusicBase)
        val apiService = retrofit.create(BaiduMusicApi::class.java)
        if (song.from == Constant.FROMLOCAL){
            songInfo = song
            listener?.OnSuccess()  //直接返回原本的song，后期可能会有加载歌词等处理，现在只是实现播放
        }else{
            apiService?.getSongLinkInfo(BaiduMusicApi.MusicFrom, BaiduMusicApi.Format, "baidu.ting.song.play", song.songId.toInt())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribe(MusicInfoObserver())
        }
    }

    fun getSongInfo():SongInfoBean{
        return songInfo!!
    }

    fun getSongLyr():SongBean.SongLrc{
        return songLrc!!
    }

    fun setOnLoadDataListener(loadDataListener: OnLoadDataListener){
        listener = loadDataListener
    }

    interface OnLoadDataListener{
        fun OnSuccess()
        fun OnFailed()
    }

    inner class MusicInfoObserver :Observer<MusicPlayResponseBean>{

        private var disposable:Disposable? = null

        override fun onError(e: Throwable?) {
            disposable!!.dispose()
            if (listener != null){
                listener!!.OnFailed()
            }
        }

        override fun onSubscribe(d: Disposable?) {
           disposable = d
        }

        override fun onComplete() {
            disposable!!.dispose()
            if (listener != null){
                listener!!.OnSuccess()
            }
        }

        /**
         * 之后可能会从新整理音乐的bean类，效率低，复用性低
         */
        override fun onNext(t: MusicPlayResponseBean?) {
            songInfo = SongInfoBean()
            songInfo?.from = Constant.FROMNET
            songInfo?.lyrLink = t?.songinfo?.lrclink
            songInfo?.coverLink = t?.songinfo?.pic_big
            songInfo?.songLink = t?.bitrate?.file_link
            songInfo?.artist = t?.songinfo?.author
            songInfo?.title = t?.songinfo?.title
        }

    }

}