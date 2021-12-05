package com.example.watchlist2.presentation.details

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
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.watchlist2.databinding.FragmentAnimeDetailsBinding
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailsFragment : Fragment() {

    private var _binding: FragmentAnimeDetailsBinding? = null

    private val binding get() = requireNotNull(_binding)

    private val animeDetailsViewModel: AnimeDetailsViewModel by viewModels()

    private val args: AnimeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animeDetailsViewModel.getAnimeDetails(args.id.toString()) // TODO

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeDetailsViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is Resource.Success -> {
                            val animeDetails = uiState.data!!
                            with(binding) {
                                backdrop.load(animeDetails.imageUrl)
                                image.load(animeDetails.imageUrl)
                                title.text = animeDetails.title
                                score.text = animeDetails.score.toString()

                                val synopsisVisibility = if (animeDetails.synopsis.isNullOrBlank()) View.GONE else View.VISIBLE
                                synopsisTitle.visibility = synopsisVisibility
                                synopsis.visibility = synopsisVisibility
                                synopsis.text = animeDetails.synopsis

                                val backgroundVisibility = if (animeDetails.background.isNullOrBlank()) View.GONE else View.VISIBLE
                                backgroundTitle.visibility = backgroundVisibility
                                background.visibility = backgroundVisibility
                                background.text = animeDetails.background
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            /* nothing */
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