package com.example.xiaojun.kotlin_try.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.util.ImageLoadUtil
import com.example.xiaojun.kotlin_try.service.MOnRecyclerViewClickListener

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MovieDoubanAdapter (private val context: Context, private val mData :List<HashMap<String,Any>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var clickListener: MOnRecyclerViewClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MovieItemHolder).movieTitle!!.text = mData[position]["title"] as String
        val ratingNum = mData[position]["score"].toString()
        holder.score!!.text = ratingNum
        holder.rating!!.rating = ratingNum.toFloat()/2
        ImageLoadUtil.loadByUrl(context, holder.posterCover, mData[position]["cover"] as String)
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
        val mView = inflater?.inflate(R.layout.item_movie_douban, parent, false)
        val holder = MovieItemHolder(mView!!)
        return holder
    }


    class MovieItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var movieTitle: TextView? = null
        var score: TextView? = null
        var posterCover: ImageView? = null
        var rating: RatingBar? = null

        init {
            movieTitle = view.findViewById(R.id.movieTitle)
            score = view.findViewById(R.id.scoreInNumber)
            rating = view.findViewById(R.id.scoreInRating)
            posterCover = view.findViewById(R.id.posterCover)
        }
    }
}