
import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.ScrollView
class MScrollView : NestedScrollView {
    private var downX: Int = 0
    private var downY: Int = 0
    private var mTouchSlop: Int = 0

    constructor(context: Context) : super(context) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.rawX.toInt()
                downY = e.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val moveY = e.rawY.toInt()
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}