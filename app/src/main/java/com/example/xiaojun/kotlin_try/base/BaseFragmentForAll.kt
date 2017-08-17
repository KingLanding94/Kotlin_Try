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
abstract class BaseFragmentForAll:Fragment(),BaseView{

    protected var root :View? = null
    protected var fragmentContent :ViewFlipper? = null
    protected var mView:View? = null
    protected var refreshTime:Long = 0
    protected var clazz:String? = null
    protected var hasBackUps = false  //数据是否在本地磁盘上有储存，如果有的话直接从磁盘上加载。

    private var loadingLayout: LinearLayout? = null
    private var loadingView: ImageView? = null
    private var tips: TextView? = null
    private var animationDrawable: AnimationDrawable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clazz = this.javaClass.name      //为了存数据使用
    }

    //目的是为了防止viewpager对它里面的fragment进行预先加载
    /**
     * 这里isVisibleToUser，如果Fragment是显示给用户的，那么这个值是true，isVisible()方法可以判断Fragment的视图是否创建好，
     * 对于第一个显示的Fragment，因为isVisibleToUSer是true，但是isVisible()是false，那么在显示第一个Fragment的时候是空白的。
     * 但是对于其它的Fragment，因为存在ViewPager的预加载，当显示到Fragment的时候，isVisible()是true，而不是false。
     * 所以会在这个方法里面进行加载数据的操作。
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        //超过十分钟再次返回页面时内容会被刷新
        if (isVisibleToUser && isVisible){
            if (refreshTime == 0.toLong() || (System.currentTimeMillis()-refreshTime) >= 10*60*1000)
            loadData()
        }
        super.setUserVisibleHint(isVisibleToUser)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater!!.inflate(R.layout.fragment_base_for_all,container,false)
        loadingLayout = root!!.findViewById(R.id.loadingLayout)
        loadingView = root!!.findViewById(R.id.loadingView)
        tips = root!!.findViewById(R.id.loadingTips)
        fragmentContent = root!!.findViewById(R.id.fragmentContent)
        setContentView(getContentView()) //设置显示的主界面
        initView()
        refreshTime = 0

        if (userVisibleHint) loadData()        //如果不设置的话，那么对于第一个fragment将会显示空白。
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
        refreshTime = System.currentTimeMillis()    //记录数据刷新时间
        animationDrawable = loadingView!!.drawable as AnimationDrawable
        animationDrawable!!.start()
    }

    /**
     * initView  子fragment初始化界面所用,初始化数据放到loadData里面调用
     */
    open fun initView(){

    }

    //保存当前fragment的请求所得到的内容
    open fun saveData(json:String){

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