package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */

//error_code	22000
//total	9156
//havemore	1
//content	[30]
data class MusicSheetResponseBean(
            var error_code:Int?,
            var total:Int?,
            var havemore:Int?,
            var content:List<MusicSheet>
        ){
    data class MusicSheet(
            var listid:String?,
            var listenum:String?,
            var collectnum:String?,
            var title:String?,
            var pic_300:String?,
            var tag:String?,
            var desc:String?
    )

}