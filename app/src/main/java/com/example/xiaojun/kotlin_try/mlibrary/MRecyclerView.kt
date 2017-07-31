package com.example.xiaojun.kotlin_try.mlibrary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/20.
 * Version 1.0.0
 */
class MRecyclerView: RecyclerView {
    /**
     * used to set clickListener
     */
    var itemClickListener: MOnRecyclerViewClickListener? = null


    constructor(context:Context):super(context){

    }

    constructor(context: Context,attributes: AttributeSet):super(context,attributes){

    }

    constructor(context: Context,attributes: AttributeSet,defStyle:Int):super(context,attributes,defStyle){

    }


}