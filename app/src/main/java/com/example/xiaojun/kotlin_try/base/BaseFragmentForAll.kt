package com.example.xiaojun.kotlin_try.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.example.xiaojun.kotlin_try.R

/**
 * Created by XiaoJun on 2017/7/29.
 * Version 1.0.0
 */
open abstract class BaseFragmentForAll:Fragment(),BaseView{

    protected var root :View? = null
    protected var fragmentContent :ViewFlipper? = null
    protected var mView:View? = null
    private var loadingLayout: LinearLayout? = null
    private var loadingView: ImageView? = null
    private var tips: TextView? = null
    private var animationDrawable: AnimationDrawable? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater!!.inflate(R.layout.fragment_base_for_all,container,false)
        loadingLayout = root!!.findViewById(R.id.loadingLayout)
        loadingView = root!!.findViewById(R.id.loadingView)
        tips = root!!.findViewById(R.id.loadingTips)
        fragmentContent = root!!.findViewById(R.id.fragmentContent)
        setContentView(getContentView()) //设置显示的主界面

        initView()
        loadData()
        return root
    }

    /**
     * 子界面的layout
     */
    abstract fun getContentView():Int

    /**
     * loadData  子fragment如果需要异步加载数据时调用的
     */
    open fun loadData(){
        animationDrawable = loadingView!!.drawable as AnimationDrawable
        animationDrawable!!.start()
    }

    /**
     * initView  子fragment初始化界面所用
     */
    open fun initView(){

    }



    private fun setContentView(res:Int){
        mView = LayoutInflater.from(activity).inflate(res,null,false)
        val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        fragmentContent?.addView(mView,lp)
    }

    override fun onSuccess() {
        doWithSuccess()
    }

    override fun onFailed() {
        doWithFailed()
    }

    override fun onGoing() {

    }

    private fun doWithSuccess(){
        animationDrawable?.stop()
        animationDrawable = null
        if (loadingLayout!!.visibility == View.VISIBLE){
            loadingLayout!!.visibility = View.GONE
        }
    }

    private fun doWithFailed(){
        animationDrawable?.stop()
        animationDrawable = null
        loadingView!!.setImageResource(R.drawable.depressed_look)
        tips!!.text = getString(R.string.loadingFailed)
    }
}