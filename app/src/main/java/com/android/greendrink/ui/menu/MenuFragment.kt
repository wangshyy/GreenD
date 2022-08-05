package com.android.greendrink.ui.menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.android.greendrink.adapter.FragmentAdapter
import com.android.greendrink.databinding.FragmentMenuBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private lateinit var viewPager2: ViewPager2
    private lateinit var horizontalTabLayout: TabLayout
    private val menuViewModel: MenuViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        viewPager2 = binding.viewpager2
        horizontalTabLayout = binding.horizontalTab
        horizontalTabLayout.apply {
            setTabTextColors(Color.BLACK, Color.parseColor("#9ACD32")) // 未选中/选中时字体颜色
            setSelectedTabIndicatorColor(Color.parseColor("#9ACD32")) // 选中时下划线颜色
            isTabIndicatorFullWidth = false //下划线不填充
        }
        viewPager2.adapter = FragmentAdapter(this)
        //将tabLayout与viewpager2关联
        TabLayoutMediator(horizontalTabLayout, viewPager2) { tab, position ->

            when (position) {
                0 -> tab.text = "经典菜单"
                1 -> tab.text = "冰爽夏日"
                2 -> tab.text = "小店推荐"
            }
            tab.view.tooltipText = "" //取消tab长按显示文字
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}