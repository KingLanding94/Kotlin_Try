package com.example.xiaojun.kotlin_try.ui.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout.LayoutParams;
import com.example.xiaojun.kotlin_try.R
import kotlinx.android.synthetic.main.layout_loading_dialog.*


/**
 * Created by XiaoJun on 2017/7/25.
 * Version 1.0.0
 */
class LoadingDialog(val mContext:Context):Dialog(mContext) {

    init {
        setContentView(R.layout.layout_loading_dialog)
        val animationDrawble = loadingView.drawable as AnimationDrawable
        animationDrawble.start()
        val window = window
        val attributesParams = window!!.attributes
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        attributesParams.dimAmount = 0.5f
        window.attributes = attributesParams
        window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }
    companion object {
        var loadingDialog:LoadingDialog? = null

        fun show(mContext: Context){

            /**
             * 防止不小心多次显示loadingDialog
             */
            if (loadingDialog != null && loadingDialog!!.isShowing){
                return
            }
            if (mContext is Activity){
                if (mContext.isFinishing){
                    return
                }
            }
            if (loadingDialog == null){
                loadingDialog = LoadingDialog(mContext)
            }

            loadingDialog!!.show()
        }

        fun dismiss(mContext: Context){
            Log.e("loading","dismiss1")
            try {
                if (mContext is Activity) {
                    if (mContext.isFinishing) {
                        loadingDialog = null
                        Log.e("loading","dismiss2")
                        return;
                    }
                }
                if (loadingDialog != null && loadingDialog!!.isShowing){
                    loadingDialog!!.dismiss()
                    Log.e("loading","dismiss3")
                }
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                loadingDialog = null
            }
        }

    }



}