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
class MusicSheetAdapter(private val context: Context, private val mData :List<HashMap<String,Any>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var clickListener: MOnRecyclerViewClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as MusicSheetViewHolder
        holder.title!!.text = mData[position]["title"] as String
        holder.playTimes!!.text = mData[position]["listened"].toString()
        holder.collectedTimes!!.text = mData[position]["collected"].toString()
        ImageLoadUtil.loadByUrl(context, holder.image, mData[position]["cover"] as String)
        if (clickListener != null) {
            holder.itemView.setOnClickListener({
                clickListener!!.onClick(holder.itemView, position)
            })
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater? = LayoutInflater.from(context)
        val mView = inflater?.inflate(R.layout.song_list_item, parent, false)
        val holder = MusicSheetViewHolder(mView!!)
        return holder
    }


    class MusicSheetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var playTimes: TextView? = null
        var title: TextView? = null
        var image: ImageView? = null
        var collectedTimes: TextView? = null

        init {
            playTimes = view.findViewById(R.id.listPlayedTimes)
            title = view.findViewById(R.id.listName)
            image = view.findViewById(R.id.listCover)
            collectedTimes = view.findViewById(R.id.collectedTimes)
        }
    }
}