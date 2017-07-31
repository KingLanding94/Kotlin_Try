package com.example.xiaojun.kotlin_try.ui.widget

import android.content.Context
import android.opengl.ETC1.getHeight
import android.support.constraint.solver.widgets.WidgetContainer.getBounds
import android.opengl.ETC1.getWidth
import android.os.Build
import android.graphics.drawable.Drawable
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.AttributeSet
import android.widget.SeekBar
import com.example.xiaojun.kotlin_try.R


/**
 * Created by XiaoJun on 2017/7/27.
 * Version 1.0.0
 */
class PlaySeekBar(context: Context) : SeekBar(context) {

//    private var drawLoading = false
//    private var degree = 0
//    private val matrix = Matrix()
//    private val loading = BitmapFactory.decodeResource(getResources(), R.drawable.play_plybar_btn_loading)
//    private var drawable: Drawable? = null
//
//    fun PlayerSeekBar(context: Context): ??? {
//        super(context)
//    }
//
//    fun PlayerSeekBar(context: Context, attrs: AttributeSet): ??? {
//        super(context, attrs)
//        setThumb(getContext().getResources().getDrawable(R.drawable.play_plybar_btn))
//    }
//
//    fun setLoading(loading: Boolean) {
//        if (loading) {
//            drawLoading = true
//            invalidate()
//        } else {
//            drawLoading = false
//        }
//    }
//
//    fun setThumb(thumb: Drawable) {
//        var localRect: Rect? = null
//        if (drawable != null) {
//            localRect = drawable!!.bounds
//        }
//        super.setThumb(drawable)
//        drawable = thumb
//        if (localRect != null && drawable != null) {
//            drawable!!.bounds = localRect!!
//        }
//    }
//
//    fun getThumb(): Drawable {
//        if (Build.VERSION.SDK_INT >= 16) {
//            return super.getThumb()
//        }
//        return drawable
//    }
//
//    @Synchronized protected fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        if (drawLoading) {
//            canvas.save()
//            degree = (degree + 3.0f).toInt()
//            degree %= 360
//            matrix.reset()
//            matrix.postRotate(degree, loading.width / 2, loading.height / 2)
//            canvas.translate(getPaddingLeft() + getThumb().bounds.left + drawable!!.intrinsicWidth / 2 - loading.width / 2 - getThumbOffset(), getPaddingTop() + getThumb().bounds.top + drawable!!.intrinsicHeight / 2 - loading.height / 2)
//            canvas.drawBitmap(loading, matrix, null)
//            canvas.restore()
//            invalidate()
//        }
//
//    }
}