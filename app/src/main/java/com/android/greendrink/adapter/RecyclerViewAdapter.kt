package com.android.greendrink.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.greendrink.data.Goods
import com.android.greendrink.databinding.ClassicRecyclerviewItemBinding

class RecyclerViewAdapter(_goodsGroupList: List<List<Goods>>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val goodsGroupList = _goodsGroupList
    class ViewHolder(binding: ClassicRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val childRecyclerView = binding.childRecyclerview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClassicRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.childRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = ChildRecyclerViewAdapter(goodsGroupList[position])
        }
    }

    override fun getItemCount(): Int = 6
}