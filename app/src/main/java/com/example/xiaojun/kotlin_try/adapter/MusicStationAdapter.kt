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
import com.example.xiaojun.kotlin_try.listener.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/21.
 * Version 1.0.0
 */
class MusicStationAdapter(private val context: Context,private val mData :List<HashMap<String,Any>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var clickListener: MOnRecyclerViewClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MusicStationViewHolder).disc!!.text = mData[position]["disc"] as String
        holder.playTimes!!.text = mData[position]["times"].toString()
        ImageLoadUtil.loadByUrl(context,holder.image,mData[position]["imageUrl"] as String)
        if (clickListener != null){
            holder.itemView.setOnClickListener({
                clickListener!!.onClick(holder.itemView,position)
            })
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater? = LayoutInflater.from(context)
        val mView = inflater?.inflate(R.layout.music_station_item,parent,false)
        val holder = MusicStationViewHolder(mView!!)
        return holder
    }


    class MusicStationViewHolder(view:View): RecyclerView.ViewHolder(view){
        var playTimes:TextView? = null
        var disc: TextView? = null
        var image:ImageView? = null

        init {
            playTimes = view.findViewById(R.id.stationPlayedTimes)
            disc = view.findViewById(R.id.radioStationName)
            image = view.findViewById(R.id.stationImage)
        }
    }
}