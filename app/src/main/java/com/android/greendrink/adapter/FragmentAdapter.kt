package com.android.greendrink.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.greendrink.ui.menu.classic.ClassicFragment
import com.android.greendrink.ui.menu.recommend.RecommendFragment
import com.android.greendrink.ui.menu.season.SeasonFragment

class FragmentAdapter(fragment:Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when(position){
            0 -> ClassicFragment()
            1 -> SeasonFragment()
            else -> RecommendFragment()
        }
        return fragment
    }
}