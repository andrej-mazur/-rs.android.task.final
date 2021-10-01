package com.example.task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.task5.R
import com.example.task5.databinding.FragmentCatBinding
import com.example.task5.ui.main.viewmodel.CatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatFragment : Fragment() {

    private var _binding: FragmentCatBinding? = null

    private val binding get() = requireNotNull(_binding)

    private val viewModel: CatViewModel by activityViewModels()

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatBinding.inflate(inflater, container, false)

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.catFlow.collectLatest { cat ->
                if (cat != null) {
                    binding.image.load(cat.url) {
                        placeholder(R.drawable.ic_placeholder)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
