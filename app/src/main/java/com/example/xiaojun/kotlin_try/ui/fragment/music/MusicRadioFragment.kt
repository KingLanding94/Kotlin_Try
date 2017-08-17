package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.CommonRecyclerViewAdapter
import com.example.xiaojun.kotlin_try.adapter.MusicStationAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForList
import com.example.xiaojun.kotlin_try.contact.MusicRadioContact
import com.example.xiaojun.kotlin_try.data.bean.MusicRadioStationResponseBean
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MusicRadioPresenter
import com.example.xiaojun.kotlin_try.ui.activity.music.SongSheetActivity
import com.example.xiaojun.kotlin_try.util.Constant
import com.example.xiaojun.kotlin_try.util.ToastUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout

class MusicRadioFragment : BaseFragmentForList(), MusicRadioContact.View {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mPresenter: MusicRadioPresenter? = null
    private var commonAdapter: CommonRecyclerViewAdapter? = null
    private var mData: MutableList<MusicRadioStationResponseBean.MusicRadioStation>? = null
    private val HorizonSpace = 30
    private val VerticalSpace = 30
    private val COLUMNS = 2


    override fun onAttach(context: Context?) {
        mPresenter = MusicRadioPresenter(context!!, this)
        super.onAttach(context)
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
        recyclerView!!.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView!!.addItemDecoration(RecyclerViewItemSpace(VerticalSpace, HorizonSpace, COLUMNS))
    }

    override fun loadData() {
        mPresenter!!.start()
    }

    override fun onSuccess() {
        super.onSuccess()
        mData = mPresenter!!.submitData()
        val stationAdapter = MusicStationAdapter(context, formatData())
        stationAdapter.clickListener = StationCLickListener()
        commonAdapter = CommonRecyclerViewAdapter(stationAdapter)
        commonAdapter!!.addFooterView(R.layout.layout_footer_no_more_data)
        recyclerView!!.adapter = commonAdapter

    }

    override fun onFailed() {
        super.onFailed()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        super.onRefresh(refreshlayout)
        ToastUtil.shortShow("已加载全部电台")
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        super.onLoadmore(refreshlayout)
        ToastUtil.shortShow("已加载全部电台")
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MusicRadioFragment {
            val fragment = MusicRadioFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    fun formatData(): ArrayList<HashMap<String, Any>> {
        val ret = ArrayList<HashMap<String, Any>>()
        for (i in mData!!) {
            val map = HashMap<String, Any>()
            map.put("disc", i.name as Any)
            map.put("times", 123)
            map.put("imageUrl", i.thumb as Any)
            ret.add(map)
        }
        return ret
    }

    inner class StationCLickListener : MOnRecyclerViewClickListener {
        override fun onClick(view: View, position: Int) {
            val intent = Intent(activity, SongSheetActivity::class.java)
            intent.putExtra("from", Constant.FROMRADIO)
            intent.putExtra("identity", mData!![position].ch_name)
            intent.putExtra("cover", mData!![position].thumb)
            context.startActivity(intent)
        }

    }
}// Required empty public constructor
