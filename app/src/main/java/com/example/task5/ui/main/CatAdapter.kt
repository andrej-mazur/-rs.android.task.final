package com.example.task5.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task5.R
import com.example.task5.api.data.Cat
import com.example.task5.databinding.CatItemBinding

class CatAdapter(private val listener: CatAdapterListener) : PagingDataAdapter<Cat, CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.let { cat ->
            holder.bind(cat, listener)
        }
    }
}

class CatViewHolder(
    private val binding: CatItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cat: Cat, listener: CatAdapterListener) {
        with(binding) {
            image.load(cat.url) {
                placeholder(R.drawable.ic_placeholder)
            }
            description.text = root.resources.getString(R.string.image_description, cat.width, cat.height)
        }
        itemView.setOnClickListener {
            listener.onClick(cat)
        }
    }
}

class CatDiffCallback : DiffUtil.ItemCallback<Cat>() {

    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.url == newItem.url
            && oldItem.height == newItem.height
            && oldItem.width == newItem.width
    }
}

interface CatAdapterListener {

    fun onClick(cat: Cat)
}
