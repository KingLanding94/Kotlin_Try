package com.example.xiaojun.kotlin_try.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/22.
 * Version 1.0.0
 */
class MusicSongListAdapter(private val mContext: Context, private val mData:List<HashMap<String,Any>>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val HIDE = -1 //不显示序号
    }

    private var clickListener: MOnRecyclerViewClickListener? = null

    public fun setOnCLickListener(listener:MOnRecyclerViewClickListener){
        this.clickListener = listener
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as SongItemHolder
        if (mData[position]["order"] as Int == HIDE){
            holder.showOrder!!.visibility = View.GONE
        }else{
            holder.showOrder!!.text = mData[position]["order"].toString()
        }
        holder.title!!.text = mData[position]["title"] as String
        holder.detail!!.text = mData[position]["detail"] as String
        /**
         * 当点击了歌曲的更多时
         */
        holder.operator!!.setOnClickListener {

        }

        if (clickListener != null){
            holder.itemView.setBackgroundResource(R.drawable.recycler_click_bg)
            holder.itemView.setOnClickListener({
                clickListener!!.onClick(holder.itemView,position)
            })
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val mView = inflater.inflate(R.layout.song_show_item,parent,false)
        val mHolder = SongItemHolder(mView)
        return mHolder
    }

    class SongItemHolder (view: View): RecyclerView.ViewHolder(view){
        var showOrder:TextView? = null
        var title: TextView? = null
        var detail: TextView? = null
        var operator: ImageView? = null

        init {
            showOrder = view.findViewById(R.id.songOrder)
            title = view.findViewById(R.id.songTitle)
            detail = view.findViewById(R.id.songDetail)
            operator = view.findViewById(R.id.songOperatorMore)
        }
    }
}