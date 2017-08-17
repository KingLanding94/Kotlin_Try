package com.example.xiaojun.kotlin_try.util

import android.app.Activity

/**
 * Created by XiaoJun on 2017/8/9.
 * Version 1.0.0
 */
class ActivityCollector {
    companion object {
        val list = ArrayList<Activity>();

        public fun addElement(activity:Activity){
            list.add(activity)
        }

        public fun removeElement(activity: Activity){
            list.remove(activity)
        }

        public fun AppExit(){
            for (activity in list){
                activity.finish()
            }
        }
    }

}