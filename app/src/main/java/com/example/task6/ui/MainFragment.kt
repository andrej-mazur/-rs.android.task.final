package com.example.task6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task6.data.TrackListing
import com.example.task6.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    private val binding get() = requireNotNull(_binding)

    lateinit var mainViewModel: MainViewModel

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

        val track = trackListing.getTracks()[0];

        binding.controls.playButton.setOnClickListener {
            mainViewModel.playOrToggleSong(track, true)
        }
        binding.controls.stopButton.setOnClickListener {
            mainViewModel.playOrToggleSong(track, false)
        }
    }

    companion object {
        fun instance() = MainFragment()
    }
}