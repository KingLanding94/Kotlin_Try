package com.example.xiaojun.kotlin_try.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.xiaojun.kotlin_try.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.*
import android.view.animation.AnimationUtils
import com.example.xiaojun.kotlin_try.adapter.CommonPagerAdapter
import com.example.xiaojun.kotlin_try.contact.TypeTabContact
import com.example.xiaojun.kotlin_try.service.FragmentTouchListener
import com.example.xiaojun.kotlin_try.ui.activity.MainActivity
import com.example.xiaojun.kotlin_try.util.Constant


/**
 * Created by Xiaojun on 2017/7/14.
 */

/**
 * this BaseFragment is just for three main fragments:movie music book
 */
abstract class BaseFragment:Fragment(),TypeTabContact.View, FragmentTouchListener,GestureDetector.OnGestureListener{


    var rootView : View? = null
    protected var tabLayout : TabLayout? = null
    protected var viewPager : ViewPager? = null
    protected var tabs = arrayListOf<String>()
    protected var fragments = arrayListOf<Fragment>()
    private var detector : GestureDetector? = null
    private var tabIsOpen = true
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.base_fragment_layout_main,container,false)
        tabLayout = rootView.findViewById(R.id.baseFragmentTabLayout)
        viewPager = rootView.findViewById(R.id.baseFragmentViewPager)
        setFragmentItems()
        initView()
        val pagerAdapter = CommonPagerAdapter(childFragmentManager,fragments,tabs)
        viewPager!!.adapter = pagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        setUpTab()
        (activity as MainActivity).registerFragmentTouch(this)
        detector = GestureDetector(activity,this)
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).unregisterFragmentTouch(this)
    }

    abstract fun setFragmentItems()

    abstract fun initView()

    //一页里面只显示四个item，如果item的数量大于四，那么更改显示模式,现在还没有大于四个的tab
    fun setUpTab(){
        if (tabs.size > Constant.TABDIVIDER){
            tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        }else{
            tabLayout!!.tabMode = TabLayout.MODE_FIXED
            tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {


        if (p0 == null || p1 == null){
            return false
        }
        //向下滑动
        if (p1!!.y - p0!!.y >50){
            if (!tabIsOpen){
                tabIsOpen = true

            }
        }

        //向上滑动
        if (p0!!.y - p1!!.y >50){
            if (tabIsOpen){
                tabIsOpen = false

            }
        }
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onTouch(ev: MotionEvent): Boolean {
        return detector!!.onTouchEvent(ev)
    }


}