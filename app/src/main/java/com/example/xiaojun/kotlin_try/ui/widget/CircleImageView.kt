package com.example.xiaojun.kotlin_try.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.xiaojun.kotlin_try.R
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Log


/**
 * Created by XiaoJun on 2017/8/11.
 * Version 1.0.0
 */

class CircleImageView :ImageView {

    private var radius:Float? = null
    private val defaultRadius = 40.toFloat()
    private var mPaint:Paint? = null
    private var mWidth:Int? = null


    constructor(context: Context):super(context){
        CircleImageView(context,null)
    }

    constructor(context: Context,attributeSet: AttributeSet?):super(context,attributeSet){
//        CircleImageView(context,attributeSet,0)  这种写法并不能调用第三个构造函数
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView);
        radius = typeArray.getDimension(R.styleable.CircleImageView_radius,defaultRadius);
        typeArray.recycle()
    }

    constructor(context: Context,attributeSet: AttributeSet?,defStyleAttributeSet:Int):super(context,attributeSet,defStyleAttributeSet){
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView);
        radius = typeArray.getDimension(R.styleable.CircleImageView_radius,defaultRadius);
        typeArray.recycle()
    }

    init {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawable == null) return
        setUpShader()
        canvas?.drawCircle(radius!!,radius!!,radius!!,mPaint)
    }

    //重写onMeasure，不过现在还没有添加对pading和wrapContent的支持
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = Math.min(measuredWidth,measuredHeight)
        setMeasuredDimension(mWidth!!, mWidth!!)
    }

    private fun setUpShader(){
        if (drawable == null) return
        val bitmap = drawableToBitmap(drawable)
        val shader = BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        var scale = 1.0f
        scale = mWidth!!*scale/Math.min(bitmap.width,bitmap.height)
        val matrix = Matrix()
        matrix.setScale(scale,scale) // 为了缩放使用
        shader.setLocalMatrix(matrix)
        mPaint!!.shader = shader
    }

    private fun getCircleBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = 0xff424242.toInt()

        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        canvas.drawARGB(0, 0, 0, 0)
        mPaint!!.color = color
        val x = bitmap.width

        canvas.drawCircle((x / 2).toFloat(), (x / 2).toFloat(), (x / 2).toFloat(), mPaint)
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, mPaint)
        return output


    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {

        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE)
            Bitmap.Config.ARGB_8888
        else
            Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)

        return bitmap
    }


}