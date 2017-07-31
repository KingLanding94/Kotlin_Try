package com.example.xiaojun.kotlin_try.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter



/**
 * Created by Xiaojun on 2017/7/14.
 */
class CommonPagerAdapter(fm:FragmentManager,private val fragments:List<Fragment>,private val tabs:List<String>):FragmentPagerAdapter(fm) {


        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getCount(): Int {
            return fragments.size
        }

        //此方法用来显示tab上的名字
        override fun getPageTitle(position: Int): CharSequence {
            return tabs[position % tabs.size]
        }

}