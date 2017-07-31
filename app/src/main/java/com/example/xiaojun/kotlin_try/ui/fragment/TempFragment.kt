package com.example.xiaojun.kotlin_try.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R

/**
 * Created by Xiaojun on 2017/7/14.
 */
class TempFragment:Fragment(){


    companion object {
        fun getInstance(color:Int): TempFragment {
            val f = TempFragment()
            val b = Bundle()
            b.putInt("color",color)
            f.arguments = b
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.temp_fragment,container,false)
        val b = arguments
        var color = b.getInt("color")
        Log.e("error","where")
        val text:TextView = view.findViewById(R.id.tempText)
        text.text = "fragment"+color
        return view
    }
}