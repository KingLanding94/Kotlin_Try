package com.example.xiaojun.kotlin_try.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.util.App
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
class MusicLocalAdapter(private val mData:List<HashMap<String,Any>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val clickListener: MOnRecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_movie_local,parent,false)
        return MovieLocalHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as MovieLocalHolder
        holder.movieTitle!!.text =  mData[position]["title"] as String
        holder.movieDetail!!.text = mData[position]["detail"] as String
        holder.duration!!.text = mData[position]["duration"].toString()
        Glide.with(App.getContext())
                .load(mData[position]["thubm"])
                .error(R.drawable.load_failed)
                .into(holder.thumb)
        if (clickListener != null){
            holder.itemView.setOnClickListener({
                clickListener.onClick(holder.itemView,position)
            })
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    class MovieLocalHolder(view: View) : RecyclerView.ViewHolder(view) {
        var duration: TextView? = null
        var thumb:ImageView? = null
        var playIcon:ImageView? = null
        var movieTitle: TextView? = null
        var movieDetail: TextView? = null

        init {
            duration = view.findViewById(R.id.duration)
            thumb = view.findViewById(R.id.playBg)
            playIcon  = view.findViewById(R.id.playMovie)
            movieDetail = view.findViewById(R.id.movieDetail)
            movieTitle = view.findViewById(R.id.movieTitle)
        }
    }


}