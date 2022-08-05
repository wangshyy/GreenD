package com.android.greendrink.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.greendrink.data.Goods
import com.android.greendrink.databinding.ChildRecyclerviewItemBinding
import com.bumptech.glide.Glide

class ChildRecyclerViewAdapter(_goodsList: List<Goods>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.ViewHolder>() {
    private val goodsList = _goodsList
    class ViewHolder(binding: ChildRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsImage = binding.goodsImage
        val goodsName = binding.goodsName
        val goodsPrice = binding.goodsPrice
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildRecyclerViewAdapter.ViewHolder {
        val binding = ChildRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.apply {
            Glide.with(itemView.context).load(goodsList[position].imageResource).into(goodsImage)
            goodsName.text = goodsList[position].name
            goodsPrice.text = goodsList[position].price.toString()
        }
    }

    override fun getItemCount(): Int =goodsList.size
}