package com.example.xiaojun.kotlin_try.mlibrary

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.xiaojun.kotlin_try.R

/**
 * Created by XiaoJun on 2017/8/21.
 * Version 1.0.0
 */

/**
 * 在MRecyclerView中Footer的存在主要是用来显示加载更多动画的，
 * 如果没有设置加载更多监听，footer也可以不是用来显示加载更多动画。
 */
class MRecyclerView : RecyclerView {


    companion object {
        val NO_LAYOUT_VALUE = -1
        //item的不同类型，item不同的时候，我们要有针对性的创建ItemView
        val HEADER = 1
        val FOOTER = -1
        val NORMAL = 0

        //几种内置的加载更多View
        val DEFAULT_LOAD_MORE_VIEW_ONE = 1
        val DEFAULT_LOAD_MORE_VIEW_TWO = 2
        val DEFAULT_LOAD_MORE_VIEW_THR = 3

        //没有更多数据了
        val NO_MORE_DATA = -1
    }

    //不同类型View的布局资源
    private var footerRes = NO_LAYOUT_VALUE
    private var itemRes = NO_LAYOUT_VALUE
    private var headerRes = NO_LAYOUT_VALUE

    //默认没有header和footer
    private var hasHeader = false
    private var hasFooter = false

    //点击事件监听
    private var itemClickedListener: OnItemClickedListener? = null
    private var headerClickedListener: OnHeaderClickListener? = null
    private var footerClickedListener: OnFooterClickedListener? = null
    private var itemLongClickedListener: OnItemLongClickedListener? = null
    //加载更多事件监听
    private var loadMoreListener: OnLoadMoreListener? = null

    private var bindDataServer: BindDataService? = null   //外部指示MRecyclerView如何绑定数据到viewHolder
    private var dataSize = 0  //要显示的数据条数
    private var hasMore = false  //是否还有更多数据

    private var loadMoreType = DEFAULT_LOAD_MORE_VIEW_ONE

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttributeSet: Int) : super(context, attributeSet, defStyleAttributeSet) {
        initAttrs(attributeSet)
    }

    private fun initAttrs(attributeSet: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.MRecyclerView)
        itemRes = typeArray.getResourceId(R.styleable.MRecyclerView_itemLayout, NO_LAYOUT_VALUE)
        headerRes = typeArray.getResourceId(R.styleable.MRecyclerView_headerLayout, NO_LAYOUT_VALUE)
        footerRes = typeArray.getResourceId(R.styleable.MRecyclerView_footerLayout, NO_LAYOUT_VALUE)
        if (headerRes != NO_LAYOUT_VALUE) hasHeader = true
        if (footerRes != NO_LAYOUT_VALUE) hasFooter = true
        typeArray.recycle()
    }

    //当外部把所有的设置都设置完成之后，调用此函数

    fun show() {

        //如果设置有加载更多的监听，那么在此注册加载更多监听
        if (loadMoreListener != null) {
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isBottom()) {
                        loadMoreListener!!.loadMore()
                    }
                }
            })
        }
        //为MRecyclerView设置适配器
        this.adapter = MRecyclerViewAdapter()
    }

    //设置要显示数据的大小
    //这个必须要设置，不然默认为0也就相当于没有数据
    fun setDataSize(size: Int) {
        dataSize = size
    }

    /**
     * 设置普通item的布局文件，在这里没有提供直接设置View的方法
     */

    fun setItemRes(res: Int) {
        this.itemRes = res
    }

    fun setFooterRes(res: Int) {
        hasFooter = true
        this.footerRes = res
    }

    fun setHeaderRes(res: Int) {
        hasHeader = true
        this.headerRes = res
    }


    //header click监听
    fun setOnHeaderClickedListener(listener: OnHeaderClickListener) {
        this.headerClickedListener = listener
    }

    //item click 监听
    fun setOnItemClickedListener(listener: OnItemClickedListener) {
        this.itemClickedListener = listener
    }

    //item longClick
    fun setOnItemLongClickedListener(listener: OnItemLongClickedListener) {
        this.itemLongClickedListener = listener
    }

    //footer click 监听
    fun setOnFooterClickedListener(listener: OnFooterClickedListener) {
        this.footerClickedListener = listener
    }


    //这个函数必须调用，否则那么RecyclerView就没有办法绑定数据。
    fun setBindDataServer(bindDataService: BindDataService) {
        this.bindDataServer = bindDataService
    }

    //设定加载更多事件监听
    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        hasFooter = true   //当设置了加载更多的监听就相当于设置了Footer，所以在此Footer设置为True
        loadMoreListener = listener
    }

    //实际上只是更改size的数值并刷新当前View，并不需要真正的把数据传进来
    fun addMoreData(dataToAdd:Int){
        if (dataToAdd == NO_MORE_DATA){ //如果没有更多数据了
            //对这种情况进行处理
            hasFooter = false
            invalidate()
        }else{
            dataSize += dataToAdd
            invalidate() //刷新界面
        }
    }

    //根据layout和viewGroup构造一个想要的viewHolder,为Adapter的onCreateViewHolder服务
    fun buildHolder(parent: ViewGroup?, layout: Int, listener: MOnClickListener?): MViewHolder {
        if (layout == MRecyclerView.NO_LAYOUT_VALUE) {
            throw RuntimeException("you forgot setting layout resources!")
        }
        val view = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return MViewHolder(view, listener)
    }

    //根据itemView和listener构造ViewHolder
    fun buildHolder(view:View,listener: MOnClickListener?):MViewHolder{
        return MViewHolder(view,listener)
    }

    /**
     * 包含了纵向和横向检查
     * computeVerticalScrollExtent()是当前屏幕显示的区域高度
     * computeVerticalScrollOffset() 是当前屏幕之前滑过的距离
     * computeVerticalScrollRange()是整个View控件的高度。
     */
    private fun isBottom(): Boolean {
        /**
         * 数据到达底部分为两种情况：横向到达底部和纵向到达底部
         */
        if (getOrientation(layoutManager) == LinearLayoutManager.VERTICAL) {
            return computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange()
        } else {
            return computeHorizontalScrollExtent() + computeHorizontalScrollOffset() >= computeHorizontalScrollRange()
        }
    }

    /**
     * 利用反射得到布局的orientation
     */
    private fun getOrientation(layoutManager: RecyclerView.LayoutManager): Int {

        var mOrientation = 0
        val clazz: Class<*>?
        try {
            //GridlayoutManager也是继承子LinearLayoutManager
            clazz = Class.forName("android.support.v7.widget.LinearLayoutManager")
            val field = clazz!!.getDeclaredField("mOrientation")
            field.isAccessible = true
            mOrientation = field.getInt(layoutManager)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return mOrientation
    }

    private fun getDefaultLoadMoreView():View {
        when (loadMoreType){
            DEFAULT_LOAD_MORE_VIEW_ONE->{
                val view = LinearLayout(context)
                view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,60)
                view.orientation = LinearLayout.HORIZONTAL
                view.gravity = Gravity.CENTER
                val progressBar = ProgressBar(context)
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                view.addView(progressBar,lp)
                return view
            }
            else->{  //和第一种相同，只是默认一种加载更多的动画
                val view = LinearLayout(context)
                view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,60)
                view.orientation = LinearLayout.HORIZONTAL
                view.gravity = Gravity.CENTER
                val progressBar = ProgressBar(context)
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                view.addView(progressBar,lp)
                return view
            }
            //现在只默认设置了一种默认加载动画
//            DEFAULT_LOAD_MORE_VIEW_TWO->{
//
//            }
//            DEFAULT_LOAD_MORE_VIEW_THR->{
//
//            }
        }

    }

    //数据显示适配器
    private inner class MRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        /**根据不同的ViewType，为MViewHolder传入不同的layout，从而构造不同类型的holder
         *现在没有对异常进行捕获
         *
         * 对于点击事件监听的设置：Header，Footer的点击事件监听在onCreateViewHolder里面设置
         * 但是NormalType的点击事件在onBindViewHolder里面设置，这样安排的主要原因是我们点击Item时，想要得到的回执数据主要是position
         */
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            when (viewType) {
                HEADER -> {
                    return buildHolder(parent, headerRes, headerClickedListener)
                }
                FOOTER -> {
                    if (footerRes != NO_LAYOUT_VALUE)
                        return buildHolder(parent, footerRes, footerClickedListener)
                    else //如果存在Footer类型，但却没有Footer布局文件的话，那么我们使用这个holder的构造器
                        return buildHolder(getDefaultLoadMoreView(),footerClickedListener)
                }
            }
            return buildHolder(parent, itemRes, itemClickedListener)
        }




        override fun getItemCount(): Int {
            var ret = dataSize
            if (hasHeader) ret++
            //如果dataSize为0，且设置了加载更多监听，那么不显示footer
            if (dataSize != 0 || loadMoreListener != null){
                if (hasFooter) ret++
            }
            return ret
        }

        //数据绑定,按照resId为View设定数值，我们在此要向外提供设定数值方法，因为像ImageView的图片加载，
        // 我们往往是通过Glide等第三方图片加载框架
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

            //如果没有提供bindServer，那么抛出异常
            if (bindDataServer == null) {
                throw NoBindDataServerException("You should provide a server binding data to viewHolder!")
            } else {
                holder as MViewHolder
                /**
                 * 当存在header的情况下，position包含了header，所以再根据position去取值得时候就会发生数组越界的问题
                 * 这个时候要将position的值减去1（对于header的position，它的值为-1，这一点我们需要注意）
                 */

                var positionModify = position
                if (hasHeader) positionModify = position - 1
                //这个应该需要修改，因为我们已经把数据提交到MRecyclerView内部了,这样写的话我们就使用不到我们提供的数据了。
                bindDataServer?.OnBindData(holder, positionModify, getItemViewType(position))
            }

        }

        //因为可能有Footer和Header，所以需要重写这个函数
        override fun getItemViewType(position: Int): Int {
            if (position == 0 && hasHeader) return HEADER
            if (position == itemCount - 1 && hasFooter) return FOOTER
            return NORMAL
        }

    }

    //ViewHolder作用：复用已经加载过的item，减少绑定布局时间
    /**
     * 通用ViewHolder功能：将不同布局里面的view数量以及类型泛化，打造一个更为通用的ViewHolder
     */
    inner class MViewHolder(val itemsView: View, val listener: MOnClickListener?) : RecyclerView.ViewHolder(itemsView) {
        private var views = SparseArray<View>()
        //通过在layout布局里的Id获取控件

        init {
            if (listener != null) {
                itemsView.setOnClickListener {
                    listener.onClick(itemsView)
                    //如果listener是ItemListener，由于我们对于ItemListener通常要多使用一项position，所以在此设定
                    if (listener is OnItemClickedListener) {
                        //如果有header，我们需要将位置减去一之后再返回
                        if (hasHeader) {
                            listener.onClick(adapterPosition - 1)
                        } else {
                            listener.onClick(adapterPosition)
                        }
                    }
                }
            }
        }

        fun getView(viewId: Int): View? {
            var view = views.get(viewId)
            if (view == null) {
                view = itemsView.findViewById(viewId)
                views.put(viewId, view)
            }
            return view
        }
    }


    //如果没有提供绑定数据方法，抛出此异常
    class NoBindDataServerException(msg: String) : Exception("NoBindServerException: " + msg) {

    }

    interface MOnClickListener {  //定义这个主要是为了使用泛型，不然的话在我们需要添加很多额外的参数
        fun onClick(view: View?)
    }

    //RecyclerView里面Item点击事件监听接口
    interface OnItemClickedListener : MOnClickListener {
        fun onClick(position: Int)
    }

    //item长按事件监听
    interface OnItemLongClickedListener {
        fun onLongClicked(position: Int)
    }

    //header点击的监听（不知道能不能实现内部具体的监听）
    interface OnHeaderClickListener : MOnClickListener {

    }

    //footer点击事件监听
    interface OnFooterClickedListener : MOnClickListener {

    }

    //为数据的绑定提供外部接口，从而得到一个通用的RecyclerView
    interface BindDataService {
        /**
         * 这个函数的有两个最主要的功能
         * 一：在MRecyclerView之外为Item里面的子View设定数值
         * 二：在MRecyclerView之外为Item里面的子View设定点击监听等
         */
        fun OnBindData(holder: MViewHolder?, position: Int, type: Int)
    }

    interface OnLoadMoreListener {
        fun loadMore()
    }

}