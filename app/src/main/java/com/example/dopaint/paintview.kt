package com.example.dopaint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.dopaint.MainActivity.Companion.brush
import com.example.dopaint.MainActivity.Companion.path

class paintview: View {
    var params:ViewGroup.LayoutParams?= null
    companion object{
        var pathlist=ArrayList<Path>()
        var colorlist=ArrayList<Int>()
        var currentbrush=Color.BLACK
    }
    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun init(){
        brush.isAntiAlias=true
        brush.color=currentbrush
        brush.style=Paint.Style.STROKE
        brush.strokeJoin=Paint.Join.ROUND
        brush.strokeWidth=8f

        params=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x= event?.x
        var y=event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                if (x != null && y!=null) {
                    path.moveTo(x,y)
                }
                return true
            }
            MotionEvent.ACTION_MOVE->{
                if(x!=null && y!=null) {
                    path.lineTo(x, y)
                }
                pathlist.add(path)
                colorlist.add(currentbrush)
            }
            else -> return false
        }
        postInvalidate()
        return false;
    }

    override fun onDraw(canvas: Canvas?) {
        for(i in pathlist.indices){
            brush.setColor(colorlist[i])
            canvas?.drawPath(pathlist[i], brush)
            invalidate()
        }
    }
}