package com.example.xiaojun.kotlin_try.data.source.local


import android.provider.MediaStore
import com.example.xiaojun.kotlin_try.data.db.DBHelper
import com.example.xiaojun.kotlin_try.util.App
import java.util.ArrayList
import com.example.xiaojun.kotlin_try.data.db.SongInfoBean
import com.example.xiaojun.kotlin_try.util.Constant


/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */

/**
 * 这个类的作用是加载本地的音乐文件
 */
class MusicLocalSource {

    /**
     * 后期再把这些数据添加到数据库里面
     */
    fun getMusicList(): ArrayList<SongInfoBean> {

        val ret = ArrayList<SongInfoBean>()
        val c = App.getContext().contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        if (c != null) {
            while (c.moveToNext()) {
                val song = SongInfoBean()
                //扫描本地文件，得到歌曲的相关信息
                song.title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE))
                song.artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                song.songPath = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                song.duration = c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                song.from = Constant.FROMLOCAL
                ret.add(song)
            }
            c.close()
        }
        return ret
    }

    fun getSongFromDb(table:String = Constant.LOCALMUSIC):ArrayList<SongInfoBean>{
        return DBHelper.getSongListFromTable(table)
    }
}