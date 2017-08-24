package com.example.xiaojun.blog.widget

import android.content.Context
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.xiaojun.kotlin_try.R

/**
 * Created by XiaoJun on 2017/8/21.
 * Version 1.0.0
 */

/**
 *  后期可能添加内容
 *  1、设置轮播动画
 *  2、设置指示器样式等
 *  3、异步加载banner的view
 */
class Banner :RelativeLayout , OnPageChangeListener{

    private val defaultTimeInterval = 1800

    private var views:List<View>? = null
    private var indicatorViews = ArrayList<View>()
    private var timeInterval = defaultTimeInterval

    private var viewPager:ViewPager? = null
    private var indicator:LinearLayout? = null   //指示器
    private var itemClickListener:OnBannerClickedListener? = null
    private var currentIndex = 0  //当前播放位置

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        setAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttributeSet: Int) : super(context, attributeSet, defStyleAttributeSet) {
        setAttrs(attributeSet)
    }

    init {
        //将viewpager添加到布局里面
        viewPager = ViewPager(context)
        //viewpager设置滑动监听
        viewPager?.addOnPageChangeListener(this)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT)
        this.addView(viewPager,layoutParams)

        indicator = LinearLayout(context)
        indicator?.orientation = LinearLayout.HORIZONTAL
        indicator?.gravity = Gravity.CENTER
        indicator?.setBackgroundColor(R.drawable.indicator_area_background)

        //将指示器添加到view里面（现在还不可以设置指示器的位置，只是默认在左下）
        val layoutParams1 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,75)
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        this.addView(indicator,layoutParams1)

        //设置自动播放（当手指按住的时候停止切换，现在还没有实现）
        this.postDelayed(object : Runnable{
            override fun run() {
                try {
                    if (viewPager != null){
                        viewPager!!.currentItem += 1
                        this@Banner.postDelayed(this,timeInterval.toLong())
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        },timeInterval.toLong())

    }

    //设置参数
    private fun setAttrs(attributeSet: AttributeSet?){
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.Banner)
        timeInterval = typeArray.getInteger(R.styleable.Banner_intervalTime,defaultTimeInterval)
        typeArray.recycle()
    }

    //初始化indicator
    private fun initIndicator(){
        //banner未初始化，或者indicator已经初始化，直接返回
        if (views == null || indicatorViews.size > 0) return
        //向indicator里面添加内容
        for (i in 0..views!!.size - 1){
            val items = ImageView(context)
            indicatorViews.add(items)
        }
        setIndicatorState()
        //将indicatorViews里面的内容添加到indicator里面
        val lp = LinearLayout.LayoutParams(30,30)
        for (i in indicatorViews){
            lp.leftMargin = 30
            indicator?.addView(i,lp)
        }
    }

    //设置indicator的显示状态
    private fun setIndicatorState(){
        for (i in 0..indicatorViews.size-1){
            if (i == currentIndex){
                indicatorViews[i].setBackgroundResource(R.drawable.indicator_point_chosen)
            }else{
                indicatorViews[i].setBackgroundResource(R.drawable.indicator_point_usual)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    //更新indicator
    override fun onPageSelected(position: Int) {
        currentIndex = position % views!!.size
        //重新设置indicator的显示状态
        setIndicatorState()
        indicator?.invalidate()  //更新指示器的显示
    }

    //设置轮播间隔时间
    fun setTimeInterval(interval:Int){
        this.timeInterval = interval
    }

    //设置轮播内容
    fun setViews(views:List<View>){
        this.views = views
        viewPager?.adapter = MPagerAdapter(views)
        //把viewPager的currentItem调到Int.MAX_VALUE的中间值
        viewPager?.currentItem = views.size * ((Int.MAX_VALUE/views.size)/2)

        //初始化indicator
        initIndicator()
    }

    //设置item点击事件监听
    fun setItemClickListener(listener:OnBannerClickedListener){
        this.itemClickListener = listener
    }

    //重写PagerAdapter来实现轮播效果
    inner class MPagerAdapter(val views:List<View>) :PagerAdapter(){

        //初始化item,Banner的item点击事件在这里设置
        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val view = views[position % views.size]
            container?.removeView(view)

            //如果设置了点击事件监听
            if (itemClickListener != null){
                view.setOnClickListener{
                    itemClickListener?.onCLick(position % views.size)
                }
            }
            container?.addView(view)
            return view
        }

        //销毁item，开始在这里销毁view的时候，但是总是报错没有销毁，所以销毁工作也改到instantiateItem里面了
        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {

        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            //这个一般直接按如下返回就行
            return view == `object`
        }

        //为了无限循环播放

        /**
         * 实现无线循环的方式可以直接在getCount里面返回Int.MAX_VALUE,
         * 但是在这种情况下有个问题：最开始的时候不可以向左滑动,这个问题有很多解决方案，我的解决方案是在设置Views的时候将
         * viewPager的currentItem调到Int.MAX_VALUE的中间数值
         */
        override fun getCount(): Int {
            return Int.MAX_VALUE
        }

    }

    //点击事件监听
    interface OnBannerClickedListener{
        fun onCLick(position:Int)
    }

}