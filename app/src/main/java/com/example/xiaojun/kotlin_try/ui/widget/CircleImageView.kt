package com.example.xiaojun.kotlin_try.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.xiaojun.kotlin_try.R
import com.example.xiaojun.kotlin_try.util.BitmapUtils


/**
 * Created by XiaoJun on 2017/8/11.
 * Version 1.0.0
 */

class CircleImageView : ImageView {

    private var radius: Float? = null
    private val defaultRadius = 40.toFloat()
    private var mPaint: Paint? = null
    private var mWidth: Int? = null


    constructor(context: Context) : super(context) {
        CircleImageView(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttributeSet: Int) : super(context, attributeSet, defStyleAttributeSet) {
        init(attributeSet)
    }

    init {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
    }

    fun init(attributeSet: AttributeSet?){
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView);
        radius = typeArray.getDimension(R.styleable.CircleImageView_radius, defaultRadius);
        typeArray.recycle()
    }
    //支持padding
    override fun onDraw(canvas: Canvas?) {
        if (drawable == null) return
        setUpShader()
        val mWidth = width - paddingLeft - paddingRight
        val mHeight = height - paddingBottom - paddingTop
        val temp = Math.min(mWidth,mHeight)
        if (radius!!*2 > temp){
            radius = temp/2f
        }
        //这个控件的radius原本是不需要的，添加radius主要是为了演示自定义View的自定义属性。

        /**
         * onDraw方法里面我们支持了padding
         * 由于我们绘制图像的时候需要将图像绘制到中心区域，所以绘制的时候我们也需要考虑padding
         */
        canvas?.drawCircle(paddingLeft+radius!!, paddingTop+radius!!, radius!!, mPaint)
    }

    //重写onMeasure，这个自定义view是继承自ImageView，可以不用重写onMeasure方法，但是如果是直接继承自View或者是ViewGroup就需要
    //重写onMeasure，否则的话，自定义控件的wrap_content的效果和match_parent一样
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(mWidth!!, mWidth!!)
    }

    /**
     *  TileMode的取值有三种：
     *  CLAMP 拉伸
     *  REPEAT 重复
     *  MIRROR 镜像
     */
    private fun setUpShader() {
        if (drawable == null) return
        val bitmap = BitmapUtils.drawableToBitmap(drawable)
        val temp = Math.min(bitmap.width,bitmap.height)
        val squareBitmap = BitmapUtils.cropBitmap(bitmap,temp,temp)
        val shader = BitmapShader(squareBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        scale = mWidth!! * scale / squareBitmap.width
        val matrix = Matrix()
        matrix.setScale(scale, scale) // 为了缩放使用
        shader.setLocalMatrix(matrix)
        mPaint!!.shader = shader
    }

}