package com.android.greendrink.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.get
import com.android.greendrink.R
import com.bumptech.glide.load.engine.Resource

class ListViewAdapter(_context: Context, _tabNameList: List<String>) : BaseAdapter() {
    private val context = _context
    private val tabNameList = _tabNameList

    //初始为0既默认选中第一个item
    private var selectItemPosition: Int = 0

    override fun getCount(): Int = tabNameList.size

    override fun getItem(p0: Int): Any = tabNameList[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var viewHolder: ViewHolder
        var view: View
        if (p1 == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.classic_tab_listview_item, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = p1
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.textView.text = tabNameList[p0]
        if (selectItemPosition == p0) {
            viewHolder.textView.setTextColor(Color.BLACK)
            view.setBackgroundColor(Color.WHITE)
            viewHolder.textView.textSize = 14f
        } else {
            viewHolder.textView.setTextColor(context.getColor(R.color.normal_text))
            view.setBackgroundColor(Color.TRANSPARENT)
            viewHolder.textView.textSize = 12f
        }
        return view
    }

    class ViewHolder(view: View) {
        var textView: TextView = view.findViewById(R.id.tab_name)
    }

    fun selectItemPosition(position: Int) {
        selectItemPosition = position
    }
}