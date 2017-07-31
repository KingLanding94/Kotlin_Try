package com.example.xiaojun.kotlin_try.ui.fragment.music

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xiaojun.kotlin_try.R

/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class MusicRecentFragment : Fragment() {

    private var mView: View? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater!!.inflate(R.layout.fragment_music_recent, container, false)
        return mView!!
    }
}