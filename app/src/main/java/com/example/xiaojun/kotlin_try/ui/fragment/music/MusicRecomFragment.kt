package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.CommonRecyclerViewAdapter
import com.example.xiaojun.kotlin_try.adapter.MusicSheetAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForList
import com.example.xiaojun.kotlin_try.contact.MusicSheetContact
import com.example.xiaojun.kotlin_try.data.bean.MusicSheetResponseBean
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewLoadMoreListener
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MusicSheetPresenter
import com.example.xiaojun.kotlin_try.ui.activity.music.SongSheetActivity
import com.example.xiaojun.kotlin_try.util.Constant
import com.scwang.smartrefresh.layout.api.RefreshLayout


class MusicRecomFragment :BaseFragmentForList(), MusicSheetContact.View{


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mData = ArrayList<MusicSheetResponseBean.MusicSheet>()
    private var showData = ArrayList<HashMap<String,Any>>()
    private var mPresenter:MusicSheetContact.Presenter? = null
    private var mAdapter:CommonRecyclerViewAdapter? = null
    private var aimAdapter:MusicSheetAdapter? = null
    private val HorizonSpace = 30
    private val VerticalSpace = 30
    private val COLUMNS = 2

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = MusicSheetPresenter(context!!,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun initView() {
        super.initView()
        recyclerView?.layoutManager = GridLayoutManager(activity,2,GridLayoutManager.VERTICAL, false)
        recyclerView?.addItemDecoration(RecyclerViewItemSpace(VerticalSpace,HorizonSpace,COLUMNS))
        aimAdapter = MusicSheetAdapter(context,showData)
        aimAdapter!!.clickListener = SheetClickListener()
        mAdapter = CommonRecyclerViewAdapter(aimAdapter!!)
        mAdapter!!.addFooterView(R.layout.layout_footer_load_more)
        mAdapter!!.setOnLoadMoreListener(object : MOnRecyclerViewLoadMoreListener {
            override fun loadMore() {
                mPresenter?.start()
                Log.e("load"," more")
            }
        },recyclerView!!)
        recyclerView?.adapter = mAdapter
    }

    override fun onSuccess() {
        super.onSuccess()
        mData.addAll(mPresenter?.submitData() as ArrayList )
        Log.e("sheet"," size "+mData.size)
        showData.clear()
        showData.addAll(formatData())
        mAdapter!!.notifyDataSetChanged()

    }

    override fun onFailed() {
        super.onFailed()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {

    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {

    }

    fun formatData():ArrayList<HashMap<String,Any>>{
        val ret = ArrayList<HashMap<String,Any>>()
        for (i in mData){
            val map = HashMap<String,Any>()
            map.put("cover",i.pic_300 as Any)
            map.put("listened",i.listenum as Any)
            map.put("collected",i.collectnum as Any)
            map.put("title",i.title as Any)
            ret.add(map)
        }
        return ret
    }

    inner class SheetClickListener : MOnRecyclerViewClickListener {
        override fun onClick(view: View, position: Int) {
            val intent = Intent(activity, SongSheetActivity::class.java)
            intent.putExtra("from", Constant.FROMSHEET)
            intent.putExtra("identity",mData[position].listid)
            intent.putExtra("cover",mData[position].pic_300)
            context.startActivity(intent)
        }

    }


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MusicRecomFragment {
            val fragment = MusicRecomFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
