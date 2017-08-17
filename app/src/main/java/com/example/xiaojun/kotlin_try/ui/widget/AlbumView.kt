package com.example.xiaojun.kotlin_try.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.data.source.remote.MusicPlayDataSource
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.MUtils
import jp.wasabeef.glide.transformations.CropCircleTransformation


/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */


class AlbumView: FrameLayout {
    var mView:View? = null
    var albumView:ImageView? = null
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    init {
        mView = LayoutInflater.from(context).inflate(R.layout.layout_album,null,false)
        albumView = mView!!.findViewById(R.id.albumImageView)
        addView(mView)
    }

    fun setAlbum(uri:String){
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.album_default)
                .bitmapTransform(CropCircleTransformation(context))
                .crossFade(1000)
                .into(albumView)

    }

    fun setAlbum(res:Int){
        Glide.with(context)
                .load(res)
                .placeholder(R.drawable.album_default)
                .bitmapTransform(CropCircleTransformation(context))
                .crossFade(1000)
                .into(albumView)

    }

    fun setAlbum(song:SongInfoBean?){
        if (song == null){
            return setAlbum(R.drawable.album_default)
        }
        if (song.from == Constant.FROMLOCAL){
            val bitmap = MUtils.getBitMapFromSong(song.songPath)
            if (bitmap != null){
                albumView?.setImageBitmap(bitmap)
                return
            }
        }

        if (song.coverPath != null)
            return setAlbum(song.coverPath)
        if (song.coverLink != null)
            return setAlbum(song.coverLink)
        /**
         * 唱片封面的加载决定在这里实现，因为在别处实现要复杂很多,歌曲无唱片信息，那么启动加载
         */
        val loadDataSource: MusicPlayDataSource = MusicPlayDataSource()
        loadDataSource.setOnLoadDataListener(object :MusicPlayDataSource.OnLoadDataListener{
            override fun OnSuccess() {
                setAlbum(loadDataSource.getSongInfo().coverLink!!)
            }

            override fun OnFailed() {
                setAlbum(R.drawable.album_default)
            }
        })
        loadDataSource.loadSongInfo(context,song)
    }


}