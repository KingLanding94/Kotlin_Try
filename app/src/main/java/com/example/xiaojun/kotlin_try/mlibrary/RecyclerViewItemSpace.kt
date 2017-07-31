package com.example.xiaojun.kotlin_try.mlibrary

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by XiaoJun on 2017/7/21.
 * Version 1.0.0
 */

/**
 * @columns: 0 表示是 LineaLayoutManager
 */
class RecyclerViewItemSpace(private val vertical:Int,private val horizon:Int,private val columns:Int)
                            : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent!!.getChildAdapterPosition(view) // item positionLineaLayoutManager
        if (columns != 0){
            val itemColumn = position % columns
            if (outRect != null) {
                outRect.left = horizon
                outRect.top = vertical
            }
        }else{
            outRect?.top = vertical
            outRect?.left = horizon
        }


    }
}