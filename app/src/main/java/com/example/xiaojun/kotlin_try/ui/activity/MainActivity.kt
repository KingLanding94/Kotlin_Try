package com.example.xiaojun.kotlin_try.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MainViewPagerAdapter
import com.example.xiaojun.kotlin_try.base.BaseActivity
import com.example.xiaojun.kotlin_try.data.db.SongInfoDao
import com.example.xiaojun.kotlin_try.listener.FragmentTouchListener
import com.example.xiaojun.kotlin_try.service.MusicPlayService
import com.example.xiaojun.kotlin_try.ui.fragment.book.BookFragment
import com.example.xiaojun.kotlin_try.ui.fragment.movie.MovieFragment
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicFragment
import com.example.xiaojun.kotlin_try.util.ActivityCollector
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, OnClickListener, ViewPager.OnPageChangeListener {

    val EXTERNAL_STORAGE_REQ_CODE = 10  //唯一标识对某个权限的请求
    var ALLOW_TO_ACCESS_STORAGE = true
    private var currIndex = 1
    private var viewPager: ViewPager? = null
    private val icons = arrayOf(R.drawable.music_icon, R.drawable.movie_icon, R.drawable.book_icon)
    private val iconsChose = arrayOf(R.drawable.music_chose, R.drawable.movie_chose, R.drawable.book_chose)
    private var imageViews: Array<ImageView>? = null
    val fragmentTouchs = ArrayList<FragmentTouchListener>()
    private var backClicks = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        imageViews = arrayOf(goToMusic, goToMovie, goToBook)
        init()

        /**
         * 一进入显示主界面就打开音乐播放服务
         */
        val startIntent = Intent(this, MusicPlayService::class.java)
        startService(startIntent)

    }

    //显示浮动按钮
    override fun showMusicStateFab(): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        backClicks = 0  //返回按钮点击的次数回0
    }

    fun init() {
        nav_view.setNavigationItemSelectedListener(this)
        viewPager = findViewById(R.id.mainContentViewPager)
        val fragments = arrayOf(MusicFragment(), MovieFragment(), BookFragment())
        val viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, fragments.asList())
        viewPager!!.adapter = viewPagerAdapter

        //第一次进入的时候默认进入的是电影栏目，所以把电影选项设置为高亮状态
        setIcons()
        viewPager!!.currentItem = currIndex
        viewPager!!.addOnPageChangeListener(this)


        goToNavigator.setOnClickListener(this)
        goToMusic.setOnClickListener(this)
        goToMovie.setOnClickListener(this)
        goToBook.setOnClickListener(this)
        goToSearch.setOnClickListener(this)

        requestPermission()  //申请内存卡读写权限

    }

    fun setIcons() {
        for (i in 0..2) {
            if (i == currIndex) {
                imageViews!![i].setImageResource(iconsChose[i])
            } else {
                imageViews!![i].setImageResource(icons[i])
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (backClicks < 2) {
                backClicks ++
                ToastUtil.shortShow(getString(R.string.click_more_to_exit))
            } else {
                //退出应用
                ActivityCollector.AppExit()
            }
        }
    }

    fun requestPermission() {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtil.longShow("I need your permisson to check out the number of songs in your device")
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        EXTERNAL_STORAGE_REQ_CODE)

            }
        } else {
            val songInfoDao = SongInfoDao();
            songInfoDao.createSongTable(Constant.LOCALMUSIC)
            songInfoDao.createSongTable(Constant.RECENTPLAY)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            EXTERNAL_STORAGE_REQ_CODE -> {
                // 如果请求被拒绝，那么通常grantResults数组为空
                ALLOW_TO_ACCESS_STORAGE = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (ALLOW_TO_ACCESS_STORAGE) {
                    Log.e("访问", "允许")
                    val songInfoDao = SongInfoDao();
                    songInfoDao.createSongTable(Constant.LOCALMUSIC)
                    songInfoDao.createSongTable(Constant.RECENTPLAY)
                }
                return
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(p0: View) {

        when (p0.id) {
            R.id.goToNavigator -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
            R.id.goToMusic -> {
                currIndex = Constant.MUSIC
                if (viewPager!!.currentItem != currIndex) {
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToMovie -> {
                currIndex = Constant.MOVIE
                if (viewPager!!.currentItem != currIndex) {
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToBook -> {
                currIndex = Constant.BOOK
                if (viewPager!!.currentItem != currIndex) {
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToSearch -> {

            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //changing,it will be executed for many times
    }

    override fun onPageSelected(position: Int) {
        currIndex = position
        setIcons()
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(mReceiver)
//        EventBus.getDefault().unregister(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        for (i in fragmentTouchs) {
            i.onTouch(ev!!)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun registerFragmentTouch(touchListener: FragmentTouchListener) {
        fragmentTouchs.add(touchListener)
    }

    fun unregisterFragmentTouch(touchListener: FragmentTouchListener) {
        fragmentTouchs.remove(touchListener)
    }

}
