package com.example.xiaojun.kotlin_try.data

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */

/**
 * create a universal response for all request
 * eh,this is useless
 */
class ResponseEntity<T>(
        var status:Int,//failed or succeed
        var response:T,//data returned from server
        var mgs:String // errorCode
){

}