package com.example.xiaojun.blog.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.example.xiaojun.kotlin_try.util.BitmapUtils
import android.util.Log
import com.example.xiaojun.kotlin_try.R


/**
 * Created by XiaoJun on 2017/8/20.
 * Version 1.0.0
 */
/**
 * CircleImageViewX是用xFerMode来实现的圆形头像
 * CircleImageViewX在头像图片的外层有一圈自定义的圆环，美化头像，默认存在圆环
 */
//直接继承自View而不再是ImageView，这个时候如果我们想实现wrap_content效果必须自己重写onMeasure

class CircleImageViewX :View{

    private val defaultWidth = 200
    private val defaultHeight = 200
    private val defaultRingWidth = 10f
    private val defaultRingColor = Color.WHITE
    private val defaultHasRing = true

    private var ringColor = defaultRingColor
    private var ringWidth = defaultRingWidth
    private var hasRing = defaultHasRing
    private var drawable:Drawable? = null


    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {

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
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageViewX)
        drawable = typeArray.getDrawable(R.styleable.CircleImageViewX_src)
        hasRing = typeArray.getBoolean(R.styleable.CircleImageViewX_hasRing,defaultHasRing)
        if (hasRing){
            ringColor = typeArray.getColor(R.styleable.CircleImageViewX_ringColor,defaultRingColor)
            ringWidth = typeArray.getDimension(R.styleable.CircleImageViewX_ringWidth,defaultRingWidth)
        }
        typeArray.recycle()
    }

    //当布局中的宽或者高属性设置的是wrap_content的时候，我们返回默认宽或者高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,defaultHeight)
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,heightSize)
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,defaultHeight)
        }else{
            setMeasuredDimension(widthSize,heightSize)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        if (drawable == null){
            Log.e("drawable","null")
            return
        }
        Log.e("drawable","draw")
        val bitmap = BitmapUtils.drawableToBitmap(drawable!!)
        val squareBitmap = BitmapUtils.cropSquareBitmap(bitmap)
        //为了支持padding
        val mWidth = width - paddingLeft - paddingRight
        val mHeight = height - paddingBottom - paddingTop
        var interval = 0
        val radius = Math.min(mWidth,mHeight)/2f
        if (hasRing){
            //画圆环
            interval = ringWidth.toInt()
            mPaint?.color = ringColor
            mPaint?.strokeWidth = ringWidth
            mPaint?.style = Paint.Style.STROKE
            //当线条有宽度的时候，paint是默认在线条宽度的中央绘制，这就要求我们在绘制外部大圆的时候有所调整
            canvas?.drawCircle(paddingLeft+radius,paddingTop+radius,radius - ringWidth/2,mPaint)
        }

        val circleBitmapRadius = radius - interval
        val circleBitmap = cropCircleBitmap(squareBitmap,circleBitmapRadius)
        //画圆形图片
        val srcRect = Rect(0,0,circleBitmap.width,circleBitmap.height)
        val desRect = Rect(paddingLeft+interval,paddingTop+interval,circleBitmap.width+paddingLeft+interval,circleBitmap.height+paddingTop+interval)
        canvas?.drawBitmap(circleBitmap,srcRect,desRect,mPaint)
    }

    //传进来的bitmap是已经裁剪好的bitmap
    fun cropCircleBitmap(bitmap: Bitmap,radius:Float):Bitmap{

        val ret = Bitmap.createBitmap(2*radius.toInt(),2*radius.toInt(), Bitmap.Config.ARGB_8888)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        //创建一个和目标图片大小相同的canvas
        val canvas = Canvas(ret)
        //绘制下层图，是一个圆
        canvas.drawCircle(radius,radius,radius,paint)
        // 设置混合模式，取绘制的图的交集部分，显示上层
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        // 第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
        val srcRect = Rect(0,0,bitmap.height,bitmap.width)
        val desRect = Rect(0,0,radius.toInt()*2,radius.toInt()*2)

        //上面两个rect的含义相当于用matrix设置scale。含义是把源图片的srcRect区域内容绘制在desRect区域内
        canvas.drawBitmap(bitmap,srcRect,desRect,paint)
        return ret
    }

    //设置圆环颜色
    fun setRingColor(color:Int){
        this.ringColor = color
        invalidate()
    }
    //设置圆环宽度
    fun setRingWidth(width:Float){
        this.ringWidth = width
        invalidate()
    }

    //设置是否有圆环
    fun setHasRing(has:Boolean){
        hasRing = has
        invalidate()
    }


}