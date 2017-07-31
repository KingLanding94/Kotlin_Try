package com.example.xiaojun.kotlin_try.data.source.local

import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.xiaojun.kotlin_try.data.bean.MovieBean
import com.example.xiaojun.kotlin_try.util.App
import java.util.ArrayList

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MovieDataSource {
    /**
     * MediaStore.Video.Media.DATA：视频文件路径；
     *MediaStore.Video.Media.DISPLAY_NAME : 视频文件名，如 testVideo.mp4
     *MediaStore.Video.Media.TITLE: 视频标题 : testVideo
     *MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
     *mimetype:媒体类型
     */
    fun getList(): MutableList<MovieBean.Video>? {
        val sysVideoList = ArrayList<MovieBean.Video>()
        val thumbColumns = arrayOf(MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID)
        val mediaColumns = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DISPLAY_NAME)
        val cursor = App.getContext().contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null)

        if (cursor == null) {
            Toast.makeText(App.getContext(), "没有找到可播放视频文件", Toast.LENGTH_SHORT).show()
            return null
        }
        if (cursor.moveToFirst()) {
            do {
                val info = MovieBean.Video()
                val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                val thumbCursor = App.getContext().contentResolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null)
                if (thumbCursor!!.moveToFirst()) {
                    info.thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA))
                }
                info.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                info.displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                info.duration = 0
                Log.e("info", "DisplayName:" + info.displayName)
                info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE))
                sysVideoList.add(info)
            } while (cursor.moveToNext())
        }
        return sysVideoList
    }
}
