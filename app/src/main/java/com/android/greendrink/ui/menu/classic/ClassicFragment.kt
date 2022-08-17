package com.android.greendrink.ui.menu.classic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.greendrink.R
import com.android.greendrink.adapter.ListViewAdapter
import com.android.greendrink.adapter.RecyclerViewAdapter
import com.android.greendrink.data.Goods
import com.android.greendrink.databinding.ClassicFragmentBinding
import com.android.greendrink.db.AppDatabase
import com.android.greendrink.db.GoodsDao
import com.android.greendrink.other.MyLinearSmoothScroller
import com.android.greendrink.other.MyListView
import com.android.greendrink.other.StickHeaderDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassicFragment : Fragment() {

    companion object {
        fun newInstance() = ClassicFragment()
    }
    private val classicViewModel: ClassicViewModel by activityViewModels()
    private var _binding: ClassicFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabListview: MyListView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var listViewAdapter: ListViewAdapter
    private lateinit var tabNameList: List<String>
    private lateinit var goodsDao: GoodsDao

    //是否为用户滑动
    private var isUserScroll: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClassicFragmentBinding.inflate(inflater, container, false)
        tabNameList = classicViewModel.tabNameList
        initTabListview()
        initRecyclerview()
        initGroupList()
        return binding.root
    }

    private fun initTabListview() {
        listViewAdapter = ListViewAdapter(binding.root.context, tabNameList)
        tabListview = binding.tabListview.apply {
            setSelector(R.color.transparent)
            adapter = listViewAdapter
            //监听listview item点击定位右侧recyclerview的top item
            setOnItemClickListener { _, _, position, _ ->
                isUserScroll = false //点击listView定位recyclerview为非用户滑动
                listViewAdapter.selectItemPosition(position)
                listViewAdapter.notifyDataSetChanged()
                scrollItemToTop(position)
            }
        }
    }

    private fun initGroupList() {
        initGoodsDao()

        classicViewModel.getGoodsGroupList(goodsDao).observe(viewLifecycleOwner) {
            Log.d("qwermain", "${it.size}")
            recyclerViewAdapter.apply {
                notifyDataSetChanged()
            }
        }
    }

    private fun initRecyclerview() {
        initGoodsDao()
        recyclerViewAdapter = RecyclerViewAdapter(classicViewModel.getGoodsGroupList(goodsDao).value?.toList())
        recyclerView = binding.classicRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            //为recyclerview添加吸顶样式
            addItemDecoration(StickHeaderDecoration(binding.root.context, tabNameList))
            //滑动监听
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var state: Int? = null
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    /**
                     * 用户滑动时为true
                     * 停止滑动false
                     **/
                    when (newState) {
                        RecyclerView.SCROLL_STATE_DRAGGING -> isUserScroll = true
                        RecyclerView.SCROLL_STATE_IDLE -> isUserScroll = false
                    }
                    state = newState
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //当滑动由用户滑动屏幕发起时，定位相应tabListItem，避免同时相互定位造成冲突
                    if (isUserScroll) {
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

    private fun initGoodsDao() {
        goodsDao = context?.let { AppDatabase.getDatabase(it).getDao() }!!
    }

    private fun insertAllGoods() {
        initGoodsDao()
        classicViewModel.insertAll(goodsDao)
    }

    //指定滑动到recyclerview top位置的item
    private fun scrollItemToTop(position: Int) {
        val smoothScroller = MyLinearSmoothScroller(binding.root.context)
        smoothScroller.targetPosition = position
        recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    //删除数据库所有表
    private fun deleteAllTable() {
        lifecycleScope.launch(Dispatchers.IO) {
            context?.let { AppDatabase.getDatabase(it).clearAllTables() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}