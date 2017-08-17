package com.example.xiaojun.kotlin_try.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewLoadMoreListener


/**
 * Created by XiaoJun on 2017/7/26.
 * Version 1.0.0
 */

/**
 * 这个对Adapter的封装是利用装饰者模式，我是刚刚从这篇博客里面学习的，里面很多代码也是比着写的，详情见：
 *                                                      http://blog.csdn.net/ywl5320/article/details/75949784
 */
open class CommonRecyclerViewAdapter(val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEAD_TYPE = 1
    val FOOT_TYPE = 10
    val NORMAL_TYPE = 100
    var mOrientation = 0
    private var footerView: View? = null
    private var headerView: View? = null
    private var loadMoreListener: MOnRecyclerViewLoadMoreListener? = null

    fun addFooterView(view: View) {
        footerView = view
    }

    fun addFooterView(res: Int) {
        val view = LayoutInflater.from(App.getContext()).inflate(res, null)
        footerView = view
    }

    fun addHeaderView(view: View) {
        headerView = view
    }

    fun addHeaderView(res: Int) {
        val view = LayoutInflater.from(App.getContext()).inflate(res, null)
        headerView = view
    }

    override fun getItemViewType(position: Int): Int {
        if (headerView != null && position == 0) {
            return HEAD_TYPE
        }
        if (footerView != null && position == itemCount - 1) {
            return FOOT_TYPE
        }
        return NORMAL_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (headerView != null && position == 0) return
        if (footerView != null && position == itemCount - 1) return
        return mAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        var size = mAdapter.itemCount
        if (footerView != null) size++
        if (headerView != null) size++
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == FOOT_TYPE) {
            return FooterViewHolder(footerView!!)
        } else if (viewType == HEAD_TYPE) {
            return HeaderViewHolder(headerView!!)
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        val layoutManager = recyclerView?.layoutManager ?: throw RuntimeException("no layoutManager set for recyclerView")
        mOrientation = getOrientation(layoutManager)
        if (layoutManager is GridLayoutManager) {
            /**
             * getSpanSize的返回值的意思是：position位置的item的宽度占几列
             * 比如总的是4列，然后头部全部显示的话就应该占4列，此时就返回4,其他的返回1
             */
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (headerView != null && position == 0) return layoutManager.spanCount
                    if (footerView != null && position == itemCount - 1) return layoutManager.spanCount
                    return 1
                }
            }
        }
    }


    fun setOnLoadMoreListener(listener: MOnRecyclerViewLoadMoreListener, mRecyclerView: RecyclerView) {
        loadMoreListener = listener
        if (loadMoreListener != null ) {
            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isBottom(mRecyclerView) ) {
                        loadMoreListener!!.loadMore()
                    }
                }
            })
        }

    }

    /**
     * 尝试一下kotlin中使用java反射
     */
    private fun getOrientation(layoutManager: RecyclerView.LayoutManager): Int {

        var mOrientation = 0
        val clazz: Class<*>?
        try {
            clazz = Class.forName("android.support.v7.widget.LinearLayoutManager")
            val field = clazz!!.getDeclaredField("mOrientation")
            field.isAccessible = true
            mOrientation = field.getInt(layoutManager)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return mOrientation
    }

    /**
     * 包含了纵向和横向检查
     * computeVerticalScrollExtent()是当前屏幕显示的区域高度
     * computeVerticalScrollOffset() 是当前屏幕之前滑过的距离
     * computeVerticalScrollRange()是整个View控件的高度。
     */
    protected fun isBottom(mRecyclerView: RecyclerView): Boolean {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            return mRecyclerView.computeVerticalScrollExtent() + mRecyclerView.computeVerticalScrollOffset() >= mRecyclerView.computeVerticalScrollRange()
        } else {
            return mRecyclerView.computeHorizontalScrollExtent() + mRecyclerView.computeHorizontalScrollOffset() >= mRecyclerView.computeHorizontalScrollRange()
        }
        return false
    }

    /**
     * 头部的ViewHolder
     */
    private inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 尾部的ViewHolder
     */
    private inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}