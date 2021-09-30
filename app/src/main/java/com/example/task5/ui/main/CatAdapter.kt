package com.example.task5.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task5.api.data.Cat
import com.example.task5.databinding.CatItemBinding

class CatAdapter : PagingDataAdapter<Cat, CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        if (cat != null) {
            holder.bind(cat)
        }
    }
}

class CatViewHolder(
    private val binding: CatItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cat: Cat) {
        with(binding) {
            image.load(cat.url)
            description.text = "${cat.width}x${cat.height}"
        }
    }
}

class CatDiffCallback : DiffUtil.ItemCallback<Cat>() {

    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }
}
