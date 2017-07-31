package com.example.xiaojun.kotlin_try.ui.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.adapter.MovieDoubanAdapter
import com.example.xiaojun.kotlin_try.base.BaseFragmentForList
import com.example.xiaojun.kotlin_try.contact.MovieTypeContact
import com.example.xiaojun.kotlin_try.data.bean.MovieDoubanResponseBean
import com.example.xiaojun.kotlin_try.mlibrary.RecyclerViewItemSpace
import com.example.xiaojun.kotlin_try.presenter.MovieListPresenter
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener
import com.example.xiaojun.kotlin_try.ui.activity.movie.MovieShowActivity
import com.example.xiaojun.kotlin_try.util.Constant
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.api.RefreshLayout

class MovieTypeFragment : BaseFragmentForList(), MovieTypeContact.View {

    // TODO: Rename and change types of parameters
    private var from: String? = null
    private var id: String? = null
    private var mPresenter: MovieTypeContact.Presenter? = null
    private var movieReponse: MovieDoubanResponseBean? = null
    private val mData = ArrayList<HashMap<String,Any>>()
    private var mAdapter:MovieDoubanAdapter? = null
    val COLUMNS = 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            from = arguments.getString(ARG_PARAM1)
            id = arguments.getString(ARG_PARAM2)
        }
        if (from!!.equals(Constant.NET)) {//网络请求
            mPresenter = MovieListPresenter(this, id!!)
        } else { //本地加载

        }
    }

    override fun initView() {
        super.initView()
        recyclerView!!.layoutManager = GridLayoutManager(activity,COLUMNS,GridLayout.VERTICAL,false)
        recyclerView!!.addItemDecoration(RecyclerViewItemSpace(COLUMNS,20,30))
    }

    override fun loadData() {
        super.loadData()
        mPresenter?.start()
    }



    override fun onSuccess() {
        super.onSuccess()
        Log.e("success","movieType")
        movieReponse = mPresenter!!.submitData()
        mData.addAll(formatData())
        mAdapter = MovieDoubanAdapter(activity,mData)
        mAdapter?.clickListener =
            object :MOnRecyclerViewClickListener{
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(activity,MovieShowActivity::class.java)
                    intent.putExtra("movie",Gson().toJson(movieReponse!!.subjects!![position]))
                    startActivity(intent)
                }
            }
        recyclerView!!.adapter = mAdapter
    }

    override fun onFailed() {
        super.onFailed()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        mData.clear()
        mPresenter?.start()

    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        mPresenter?.start()
    }


    fun formatData():List<HashMap<String,Any>>{
        val ret = ArrayList<HashMap<String,Any>>()
        for (i in movieReponse!!.subjects){
            val map = HashMap<String,Any>()
            map.put("title",i.title!!)
            map.put("score",i.rating!!.average!!)
            map.put("cover",i.images!!.large!!)
            ret += map
        }
        return ret
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "from"  //第一个参数用来标示数据是否来自网络
        private val ARG_PARAM2 = "id"   //第二个参数来区别目标网址(影院热映)

        fun newInstance(from: String, id: String): MovieTypeFragment {
            val fragment = MovieTypeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, from)
            args.putString(ARG_PARAM2, id)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
