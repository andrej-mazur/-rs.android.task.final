package com.example.task6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.task6.data.TrackListing
import com.example.task6.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()

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

        binding.log.text = trackListing.getTracks().joinToString(
            separator = "\n",
            transform = { track -> track.title }
        )
    }

    companion object {
        fun instance() = MainFragment()
    }
}