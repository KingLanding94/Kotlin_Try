package com.example.xiaojun.kotlin_try.data.bean

import android.view.Display
import java.io.Serializable

/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */
/**
 * 电影bean类的原型
 * thumb:缩略图
 */
data class MovieBean(var media: MediaBean? = null,
                     var size: String? = ""
):Serializable {
    data class Video(
            val id: Int? = 0,
            var path: String? = null,
            var title: String? = null,
            var displayName:String? = null,
            var thumbPath: String? = null,
            var mimeType:String? = null,
            var duration:Int? = 0,
            var size: String? = ""
            ):Serializable
}