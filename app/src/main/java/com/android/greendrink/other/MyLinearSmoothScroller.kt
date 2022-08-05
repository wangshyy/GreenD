package com.android.greendrink.other

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY

class MyLinearSmoothScroller(_context: Context) : LinearSmoothScroller(_context) {
    //speed越大滑动速度越慢
    private var speed = 0.03f
    private val context = _context

    /*
     * item置顶
     * SNAP_TO_START：将position位置item的top和left与父top和left对齐,实现置顶效果
    */
    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
        setScrollSpeed()
        return speed / displayMetrics!!.density
    }

    private fun setScrollSpeed(){
        speed = context.resources.displayMetrics.density * 0.05f
    }
}