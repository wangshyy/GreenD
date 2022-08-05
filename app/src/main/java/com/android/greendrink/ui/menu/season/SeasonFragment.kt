package com.android.greendrink.ui.menu.season

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.greendrink.R

class SeasonFragment : Fragment() {

    companion object {
        fun newInstance() = SeasonFragment()
    }

    private val viewModel: SeasonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.season_fragment, container, false)
    }


}