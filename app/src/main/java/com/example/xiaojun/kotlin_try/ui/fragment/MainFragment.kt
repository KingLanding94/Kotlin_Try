package com.example.xiaojun.kotlin_try.ui.fragment

/**
 * Created by Xiaojun on 2017/7/14.
 */
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xiaojun.kotlin_try.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.example.xiaojun.kotlin_try.adapter.CommonPagerAdapter

/**
 * Created by Xiaojun on 2017/7/14.
 */
class MainFragment : Fragment() {

    var rootView : View? = null
    var tabLayout : TabLayout? = null
    var viewPager : ViewPager? = null
    val tabs = arrayListOf<String>()
    val fragments = arrayListOf<Fragment>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.base_fragment_layout_main,container,false)
        tabLayout = rootView.findViewById(R.id.baseFragmentTabLayout)
        viewPager = rootView.findViewById(R.id.baseFragmentViewPager)
        setTabItems()
        setFragments()
        val pagerApdater = CommonPagerAdapter(childFragmentManager,fragments,tabs)

        viewPager!!.adapter = pagerApdater
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout!!.setTabMode(TabLayout.MODE_FIXED);
        return rootView
    }

    fun setTabItems(){
        val mTypesChinese = arrayOf("犯罪", "科幻", "战争", "喜剧")
        tabs += mTypesChinese
    }

    fun setFragments(){
        val mColors = arrayOf(R.color.grayLight,R.color.red,R.color.black,R.color.colorAccent)
        mColors.mapTo(fragments) { TempFragment.getInstance(it) }
    }
}