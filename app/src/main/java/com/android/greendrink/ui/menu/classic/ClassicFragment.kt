package com.android.greendrink.ui.menu.classic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.greendrink.R
import com.android.greendrink.adapter.ListViewAdapter
import com.android.greendrink.adapter.RecyclerViewAdapter
import com.android.greendrink.data.Goods
import com.android.greendrink.databinding.ClassicFragmentBinding
import com.android.greendrink.other.MyLinearSmoothScroller
import com.android.greendrink.other.MyListView
import com.android.greendrink.other.StickHeaderDecoration

class ClassicFragment : Fragment() {

    companion object {
        fun newInstance() = ClassicFragment()
    }

    private val classicViewModel: ClassicViewModel by viewModels()
    private var _binding: ClassicFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabListview: MyListView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var listViewAdapter: ListViewAdapter
    private lateinit var tabNameList: List<String>
    private lateinit var goodsGroupList: List<List<Goods>>
    //是否为用户滑动
    private var isUserScroll: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClassicFragmentBinding.inflate(inflater, container, false)
        tabNameList = classicViewModel.tabNameList
        goodsGroupList = classicViewModel.goodsGroupList
        initTabListview()
        initRecyclerview()
        return binding.root
    }

    private fun initTabListview() {
        listViewAdapter = ListViewAdapter(binding.root.context, tabNameList)
        tabListview = binding.tabListview.apply {
            setSelector(R.color.transparent)
            adapter = listViewAdapter
            //监听listview item点击定位右侧recyclerview的position
            setOnItemClickListener { _, _, position, _ ->
                isUserScroll = false //点击listView定位recyclerview为非用户滑动
                listViewAdapter.selectItemPosition(position)
                listViewAdapter.notifyDataSetChanged()
                scrollItemToTop(position)
            }
        }
    }

    private fun initRecyclerview() {
        recyclerViewAdapter = RecyclerViewAdapter(goodsGroupList)
        recyclerView = binding.classicRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            //为recyclerview添加吸顶样式
            addItemDecoration(StickHeaderDecoration(binding.root.context, tabNameList))

            addOnScrollListener(object: RecyclerView.OnScrollListener(){

                var state: Int? = null
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                     /**
                      * 用户滑动时为true
                      * 停止滑动false
                     **/
                    when(newState){
                        RecyclerView.SCROLL_STATE_DRAGGING -> isUserScroll = true
                        RecyclerView.SCROLL_STATE_IDLE -> isUserScroll = false
                    }
                    state = newState
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //当滑动由用户滑动屏幕发起时，定位相应tabListItem
                    //主要防止同时相互定位造成冲突
                    if (isUserScroll){
                        if (dy != 0) {
                            val position =
                                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() //获取列表里当前第一个可见的item
                            listViewAdapter.apply {
                                selectItemPosition(position)
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
        }
    }

    //指定滑动到recyclerview top位置的item
    private fun scrollItemToTop(position: Int) {
        val smoothScroller = MyLinearSmoothScroller(binding.root.context)
        smoothScroller.targetPosition = position
        recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}