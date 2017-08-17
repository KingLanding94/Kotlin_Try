package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.example.xiaojun.kotlin_try.adapter.MusicRankAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForList
import com.example.xiaojun.kotlin_try.contact.MusicRankContact
import com.example.xiaojun.kotlin_try.data.bean.MusicRankResponseBean
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MusicRankPresenter
import com.example.xiaojun.kotlin_try.ui.activity.music.SongSheetActivity
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.ToastUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout


class MusicRankFragment : BaseFragmentForList(),MusicRankContact.View {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mPresenter:MusicRankContact.Presenter? = null
    private var mData:ArrayList<MusicRankResponseBean.MusicRank>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mPresenter = MusicRankPresenter(context!!,this)
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
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(RecyclerViewItemSpace(20,0,0))
    }

    override fun loadData() {
        super.loadData()
        mPresenter?.start()

    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        super.onRefresh(refreshlayout)
        ToastUtil.shortShow("已加载全部排行榜")
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        super.onLoadmore(refreshlayout)
        ToastUtil.shortShow("已加载全部排行榜")
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(param1: String, param2: String): MusicRankFragment {
            val fragment = MusicRankFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSuccess() {
        super.onSuccess()
        mData = mPresenter?.submitData() as ArrayList<MusicRankResponseBean.MusicRank>
        val adapter = MusicRankAdapter(formatData(mData!!),activity)
        adapter.clickListener = RankClickListener()
        recyclerView?.adapter = adapter
    }

    fun formatData(mData:List<MusicRankResponseBean.MusicRank>):ArrayList<HashMap<String,Any>>{
        var ret = ArrayList<HashMap<String,Any>>()
        for (i in mData){
            val map = HashMap<String,Any>()
            map.put("cover",i.pic_s260 as Any)
            map.put("one",i.content[0].title+i.content[0].author)
            map.put("two",i.content[1].title+" - "+ i.content[1].author)
            map.put("three",i.content[2].title+i.content[2].author)
            ret.add(map)
        }
        return ret
    }

    inner class RankClickListener : MOnRecyclerViewClickListener {
        override fun onClick(view: View, position: Int) {
            val intent = Intent(activity, SongSheetActivity::class.java)
            intent.putExtra("from",Constant.FROMRANK)
            intent.putExtra("identity",mData!![position].type.toString())
            intent.putExtra("cover",mData!![position].pic_s260)
            context.startActivity(intent)
        }

    }

}
