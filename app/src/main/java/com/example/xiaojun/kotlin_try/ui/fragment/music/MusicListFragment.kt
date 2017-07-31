package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MusicListOfMenuAdapter
import com.example.xiaojun.kotlin_try.contact.MusicListContact
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.presenter.MusicListPresenter
import com.example.xiaojun.kotlin_try.ui.activity.music.LocalSongSheetActivity


class MusicListFragment : Fragment(),MusicListContact.View {

    /**
     * 如果用户并没有打开过这个页面，那么这个页面一直都不会被加载
     */

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mView: View? = null
    private val mPresenter = MusicListPresenter(this)
    private var mData: List<HashMap<String,Any>>? = null
    private var menuList: RecyclerView? = null

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): MusicListFragment {
            val fragment = MusicListFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mView = inflater!!.inflate(R.layout.fragment_music_list, container, false)
        initView()
        return mView
    }

    fun initView(){
        menuList = mView!!.findViewById(R.id.musicListRecyclerView)
        menuList!!.layoutManager = LinearLayoutManager(activity)
        mPresenter.start()
    }

    override fun onSuccess() {
        mData = mPresenter.submitData()
        val adapter =  MusicListOfMenuAdapter(activity,mData!!)
        adapter.clickListener = RecyclerViewClick()
        menuList!!.adapter = adapter

    }

    override fun onFailed() {
        //nothing
    }

    override fun onGoing() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * set item click listener
     */
    inner class RecyclerViewClick : MOnRecyclerViewClickListener {
        override fun onClick(view: View, position: Int) {
            when(position){

            }
            startActivity(Intent(this@MusicListFragment.activity, LocalSongSheetActivity::class.java))
        }

    }



}// Required empty public constructor
