package com.example.xiaojun.kotlin_try.adapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created by Xiaojun on 2017/7/14.
 */
class MainViewPagerAdapter(fm:FragmentManager,private val fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm){

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }
}