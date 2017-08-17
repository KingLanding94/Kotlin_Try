package com.example.xiaojun.kotlin_try.util

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Created by XiaoJun on 2017/8/16.
 * Version 1.0.0
 */

/**
 * 对于网络上的数据，每次加载都从网络加载比较耗时，而且比较费流量，如果用户没有主动刷新而且磁盘上存有备份的时候，我们直接调用就好了。
 * 每次加载本地音乐的时候，速度很慢
 */
object SaveStateUtil {

    val basePath = App.getContext().filesDir.absolutePath+"/"

    //这样会把以前的数据清空吗？
    fun saveData(json:String,name:String){
        val fileOutput = FileOutputStream(name)
        fileOutput.write(json.toByteArray())
        fileOutput.close()
    }

    fun readData(name:String):String?{
        val file = File(basePath+name)
        if (file.exists()){
            val fileInputStream = FileInputStream(file)
            var buffer = ByteArray(1024)
            val stringBuffer = StringBuffer()
            var read = 0
            do {
                read = fileInputStream.read(buffer)
                if (read != -1){
                    stringBuffer.append(String(buffer,0,read))
                }else{
                    break
                }
            }while (true)
            fileInputStream.close()
            return stringBuffer.toString()
        }
        return null
    }

}