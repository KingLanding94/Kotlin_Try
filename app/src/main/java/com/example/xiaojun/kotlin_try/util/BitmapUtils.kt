package com.example.xiaojun.kotlin_try.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * Created by XiaoJun on 2017/8/20.
 * Version 1.0.0
 */
object BitmapUtils {

    //将drawable转换成bitmap
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

    /**
     * 裁剪
     * @param bitmap 原图
     * *
     * @return 裁剪后的图像
     */
    fun cropBitmap(bitmap: Bitmap, aimWidth:Int, aimHeight:Int): Bitmap {
        var toWidth = aimWidth;
        var toHeight = aimHeight
        if (aimWidth > bitmap.width) toWidth = bitmap.width
        if (aimHeight > bitmap.height) toHeight = bitmap.height

        val cropWidthSide = (bitmap.width - aimWidth)/2
        val cropHeightSide = (bitmap.height - aimHeight)/2
        return Bitmap.createBitmap(bitmap,cropWidthSide,cropHeightSide,toWidth,toHeight)
    }

    //将bitmap裁剪成方形
    fun cropSquareBitmap(bitmap:Bitmap):Bitmap{
        val width = Math.min(bitmap.width,bitmap.height)
        return cropBitmap(bitmap,width,width)
    }
}