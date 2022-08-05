package com.android.greendrink.ui.menu.recommend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.greendrink.R

class RecommendFragment : Fragment() {

    companion object {
        fun newInstance() = RecommendFragment()
    }

    private val viewModel: RecommendViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recommend_fragment, container, false)
    }



}