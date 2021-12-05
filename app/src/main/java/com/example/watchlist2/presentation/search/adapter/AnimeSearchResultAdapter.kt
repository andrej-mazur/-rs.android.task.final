package com.example.watchlist2.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.watchlist2.databinding.AnimeSearchResultItemBinding
import com.example.watchlist2.domain.model.AnimeSearchResult

class AnimeSearchResultAdapter : ListAdapter<AnimeSearchResult, AnimeSearchResultViewHolder>(AnimeSearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSearchResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AnimeSearchResultItemBinding.inflate(layoutInflater, parent, false)
        return AnimeSearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeSearchResultViewHolder, position: Int) {
        val animeSearchResult = getItem(position)
        if (animeSearchResult != null) {
            holder.bind(animeSearchResult)
        }
    }
}

class AnimeSearchResultViewHolder(
    private val binding: AnimeSearchResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animeSearchResult: AnimeSearchResult) {
        with(binding) {
            image.load(animeSearchResult.imageUrl)
            title.text = animeSearchResult.title
        }
    }
}

class AnimeSearchResultDiffCallback : DiffUtil.ItemCallback<AnimeSearchResult>() {

    override fun areItemsTheSame(oldItem: AnimeSearchResult, newItem: AnimeSearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeSearchResult, newItem: AnimeSearchResult): Boolean {
        return oldItem == newItem
    }
}