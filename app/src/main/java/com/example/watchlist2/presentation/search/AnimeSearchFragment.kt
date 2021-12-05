package com.example.watchlist2.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist2.R
import com.example.watchlist2.databinding.FragmentAnimeSearchBinding
import com.example.watchlist2.extension.setToolbarTitle
import com.example.watchlist2.presentation.search.adapter.AnimeSearchResultAdapter
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeSearchFragment : Fragment() {

    private var _binding: FragmentAnimeSearchBinding? = null

    private val binding get() = _binding!!

    private val animeSearchResultAdapter = AnimeSearchResultAdapter()

    private val animeSearchViewModel: AnimeSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeSearchBinding.inflate(inflater, container, false)
        setToolbarTitle(R.string.search)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            movieList.layoutManager = LinearLayoutManager(context)
            movieList.adapter = animeSearchResultAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeSearchViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is Resource.Success -> {
                            animeSearchResultAdapter.submitList(uiState.data)
                            binding.progress.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                            binding.progress.visibility = View.GONE
                        }
                        is Resource.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}