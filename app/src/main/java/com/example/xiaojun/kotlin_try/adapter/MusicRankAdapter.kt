package com.example.xiaojun.kotlin_try.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.util.ImageLoadUtil
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/23.
 * Version 1.0.0
 */
class MusicRankAdapter(private val mData:List<HashMap<String,Any>>,private val mContext:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickListener: MOnRecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val mView = inflater.inflate(R.layout.music_rank_item,parent,false)
        val holder = RankHolder(mView)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as RankHolder
        ImageLoadUtil.loadByUrl(mContext,holder.cover,mData[position]["cover"] as String)
        holder.one?.text = mData[position]["one"] as String
        holder.two?.text = mData[position]["two"] as String
        holder.three?.text = mData[position]["three"] as String

        if(clickListener != null){
            holder.itemView.setOnClickListener({
                clickListener?.onClick(holder.itemView,position)
            })
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class RankHolder(mView:View):RecyclerView.ViewHolder(mView){
        var cover:ImageView? = null
        var one:TextView? = null
        var two:TextView? = null
        var three:TextView? = null
        init {
            cover = mView.findViewById(R.id.rankCover)
            one = mView.findViewById(R.id.one)
            two = mView.findViewById(R.id.two)
            three = mView.findViewById(R.id.three)
        }
    }
}