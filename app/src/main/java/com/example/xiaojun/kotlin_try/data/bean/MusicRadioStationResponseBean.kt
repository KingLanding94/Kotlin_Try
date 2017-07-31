package com.example.xiaojun.kotlin_try.data.bean

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * 问题描述1：调用百度音乐Api的时候，当我们想要查询电台列表，返回的json里面会有Error_code这个
 * 栏目，从而影响了对内容的具体解析。从Eyepetizer里面受到的启发是：对于每个页面的内容都只是一个Bean。
 * 所以我也在bean里面加有list和error_code了
 *
 *  问题描述2：对于服务器返回数据的解析是在Retrofit里面设置的自动解析，对于自动解析，我们要把bean的变量名称
 *  命名成服务器返回json的变量名
 */
data class MusicRadioStationResponseBean(var error_code:Int,var result:List<MusicRadioStationLists>){


    data class MusicRadioStationLists(var title:String,var cid:Int,var channellist:List<MusicRadioStation>)

    data class MusicRadioStation(
            var thumb : String?,
            var name: String?,
            var channelid: String?,
            var ch_name:String?,  //used for search
//            var value:Int?,
            var cate_name:String?,
            var cate_sname:String?,
            var artistid:String?,
            var avatar:String?)

}
