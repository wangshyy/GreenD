package com.android.greendrink.other

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.greendrink.adapter.RecyclerViewAdapter
import kotlin.math.min

class StickHeaderDecoration(
    val context: Context, private val tabNameList: List<String>
) : RecyclerView.ItemDecoration() {
    private val itemHeaderHeight = dip2px(context, 46f)
    private val textPaddingLeft = dip2px(context, 10f)
    private var textRect = Rect()

    private val itemHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 40F
        color = Color.BLACK
    }

    /**
     * 设置Item的间距
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = itemHeaderHeight
    }

    /**
     * 绘制Item组头,分割线绘制也在此方法中
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val count = parent.childCount
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        for (i in 0 until count) {
            val itemView = parent.getChildAt(i)
            val itemViewPosition = parent.getChildLayoutPosition(itemView)
            val top = (itemView.top - itemHeaderHeight).toFloat()
            val bottom = (itemView.top).toFloat()
            c.drawRect(left, top, right, bottom, itemHeaderPaint)
            textPaint.getTextBounds(
                tabNameList[itemViewPosition],
                0,
                tabNameList[itemViewPosition].length,
                textRect
            )
            val x = left + textPaddingLeft
            val y =
                ((itemView.top - itemHeaderHeight) + itemHeaderHeight / 2 + textRect.height() / 2).toFloat()
            c.drawText(tabNameList[itemViewPosition], x, y, textPaint)
        }
    }


    /**
     * 绘制Item的顶部布局（吸顶效果）
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val view: View = parent.findViewHolderForAdapterPosition(position)!!.itemView
        val top = parent.paddingTop.toFloat()
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        val bottom = min(itemHeaderHeight, view.bottom).toFloat()
        c.drawRect(left, top + view.top - itemHeaderHeight, right, top + bottom, itemHeaderPaint)
        textPaint.getTextBounds(
            tabNameList[position],
            0,
            tabNameList[position].length,
            textRect
        )
        val x = left + textPaddingLeft
        val y = top + itemHeaderHeight / 2 + textRect.height() / 2 - (itemHeaderHeight-bottom)
        c.drawText(tabNameList[position],x,y,textPaint)
    }


    //dp转px
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}

