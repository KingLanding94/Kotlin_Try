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
 * Created by XiaoJun on 2017/7/17.
 * Version 1.0.0
 */
class MusicListOfMenuAdapter(private val mContext:Context,private val mItems:List<HashMap<String,Any>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickListener: MOnRecyclerViewClickListener? = null

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MHolder).icon!!.setImageResource(mItems[position]["icon"] as Int)
        holder.title!!.text = mItems[position]["title"] as String
        val n = mItems[position]["number"] as Int
        holder.numbers!!.text = n.toString()
        if (clickListener != null){
            holder.itemView.setBackgroundResource(R.drawable.recycler_click_bg)
            holder.itemView.setOnClickListener(View.OnClickListener {
                clickListener!!.onClick(holder.itemView,position)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val mInflater = LayoutInflater.from(mContext)
        val view = mInflater.inflate(R.layout.music_list_item,parent,false)
        val holder = MHolder(view)
        return holder
    }



    class MHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var icon: ImageView? = null
        var title: TextView? = null
        var numbers: TextView? = null

        init{
            icon = itemView.findViewById(R.id.musicListItemIcon)
            title = itemView.findViewById(R.id.musicListItemTitle)
            numbers = itemView.findViewById(R.id.musicListCapacity)
        }

    }


}