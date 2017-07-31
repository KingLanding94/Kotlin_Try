package com.example.xiaojun.kotlin_try.data.bean

import java.security.cert.CertPath

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * title:name of the media
 * id:id
 * path:local storage path
 * coverPath:
 */
open class MediaBean(
    var title: String?,
    var id: Int?,
    var path: String?,
    var coverPath:String?) {
}