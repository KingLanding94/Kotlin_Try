package com.example.xiaojun.kotlin_try.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MainViewPagerAdapter
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.service.FragmentTouchListener
import com.example.xiaojun.kotlin_try.service.MusicPlayService
import com.example.xiaojun.kotlin_try.service.MusicPlayingStateEvent
import com.example.xiaojun.kotlin_try.ui.activity.music.MusicPlayActivity
import com.example.xiaojun.kotlin_try.ui.fragment.book.BookFragment
import com.example.xiaojun.kotlin_try.ui.fragment.movie.MovieFragment
import com.example.xiaojun.kotlin_try.ui.fragment.music.MusicFragment
import com.example.xiaojun.kotlin_try.util.Constant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnClickListener,ViewPager.OnPageChangeListener{

    private var currIndex = 1
    private var viewPager : ViewPager? = null
    private val icons = arrayOf(R.drawable.music_icon,R.drawable.movie_icon,R.drawable.book_icon)
    private val iconsChose = arrayOf(R.drawable.music_chose,R.drawable.movie_chose,R.drawable.book_chose)
    private var imageViews:Array<ImageView>? = null
    var animationDrawble :AnimationDrawable? = null
    val mReceiver = UpdateViewReceiver()
    val fragmentTouchs = ArrayList<FragmentTouchListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        imageViews = arrayOf(goToMusic,goToMovie,goToBook)

        init()
        animationDrawble = fab.drawable as AnimationDrawable

        /**
         * 注册广播
         */
        val filter = IntentFilter()
        filter.addAction(Constant.UPDATEVIEWACTION)
        registerReceiver(mReceiver, filter)

        EventBus.getDefault().register(this)

        /**
         * 一进入显示主界面就打开音乐播放服务
         */
        val startIntent = Intent(this, MusicPlayService::class.java)
        startService(startIntent)

    }

    //向playService发送请求，请求当前的播放状态
    override fun onResume() {
        super.onResume()
        val intent = Intent()
        intent.action = Constant.PLAYCONTROLACTION
        intent.putExtra("what",Constant.ISPLAYING)
        sendBroadcast(intent)
    }

    fun init(){
        /**
         * 点击这个界面以后，跳转到音乐播放界面
         */
        fab.setOnClickListener { view ->
            startActivity(Intent(App.getContext(), MusicPlayActivity::class.java))
        }
        nav_view.setNavigationItemSelectedListener(this)
        viewPager = findViewById(R.id.mainContentViewPager)
        val fragments = arrayOf(MusicFragment(), MovieFragment(), BookFragment())
        val viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager,fragments.asList())
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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPlayingState(event:MusicPlayingStateEvent){
        if(event.isPlaying){
            if (animationDrawble != null && !animationDrawble!!.isRunning)
                animationDrawble?.start()
        }else{
            if (animationDrawble != null && animationDrawble!!.isRunning)
                animationDrawble?.stop()
        }
    }


    fun setIcons(){
        for (i in 0..2){
            if (i == currIndex){
                imageViews!![i].setImageResource(iconsChose[i])
            }else{
                imageViews!![i].setImageResource(icons[i])
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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

        when(p0.id){
            R.id.goToNavigator->{
                if (drawer_layout.isDrawerOpen(GravityCompat.START)){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
            R.id.goToMusic->{
                currIndex = Constant.MUSIC
                if (viewPager!!.currentItem != currIndex){
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToMovie->{
                currIndex = Constant.MOVIE
                if (viewPager!!.currentItem != currIndex){
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToBook->{
                currIndex = Constant.BOOK
                if (viewPager!!.currentItem != currIndex){
                    viewPager!!.currentItem = currIndex
                }
            }
            R.id.goToSearch->{

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
        unregisterReceiver(mReceiver)
        EventBus.getDefault().unregister(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        for (i in fragmentTouchs){
            i.onTouch(ev!!)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun registerFragmentTouch(touchListener: FragmentTouchListener){
        fragmentTouchs.add(touchListener)
    }

    fun unregisterFragmentTouch(touchListener: FragmentTouchListener){
        fragmentTouchs.remove(touchListener)
    }

    inner class UpdateViewReceiver:BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val WHAT = p1!!.extras.getInt("what")
            when(WHAT){
                Constant.PLAYACTION->{
                    animationDrawble?.start()
                }
                Constant.PAUSEACTION->{
                    animationDrawble?.stop()
                }
            }
        }
    }
}
