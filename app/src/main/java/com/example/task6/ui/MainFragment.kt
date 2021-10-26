package com.example.task6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.task6.R
import com.example.task6.data.TrackListing
import com.example.task6.databinding.MainFragmentBinding
import com.example.task6.exoplayer.extensions.isPlaying
import com.example.task6.exoplayer.extensions.toTrack
import com.example.task6.other.Status.ERROR
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    private val binding get() = requireNotNull(_binding)

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var trackListing: TrackListing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.log.text = trackListing.getTracks().joinToString(
            separator = "\n",
            transform = { track -> track.title }
        )

        mainViewModel.isConnected.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> Snackbar.make(binding.root, result.message ?: "An unknown error occurred", Snackbar.LENGTH_LONG).show()
                    else -> Unit
                }
            }
        }

        mainViewModel.networkError.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> Snackbar.make(binding.root, result.message ?: "An unknown error occurred", Snackbar.LENGTH_LONG).show()
                    else -> Unit
                }
            }
        }

        mainViewModel.playbackState.observe(viewLifecycleOwner) { playbackState ->
            val playbackStateImage = if (playbackState?.isPlaying == true) R.drawable.ic_pause_24 else R.drawable.ic_play_24
            with(binding) {
                controls.playOrPauseButton.setImageResource(playbackStateImage)
            }
        }

        mainViewModel.curPlayingSong.observe(viewLifecycleOwner) { mediaMetadata ->
            if (mediaMetadata == null) return@observe
            val track = mediaMetadata.toTrack()
            if (track != null) {
                glide.load(track.bitmapUri).into(binding.image)
                binding.title.text = "${track.artist} - ${track.title}"
            }
        }

        binding.controls.playOrPauseButton.setOnClickListener {
            mainViewModel.playOrPause()
        }

        binding.controls.nextButton.setOnClickListener {
            mainViewModel.skipToNextSong()
        }

        binding.controls.prevButton.setOnClickListener {
            mainViewModel.skipToPreviousSong()
        }
    }

    companion object {
        fun instance() = MainFragment()
    }
}
