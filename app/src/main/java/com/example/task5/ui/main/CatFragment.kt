package com.example.task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.task5.R
import com.example.task5.databinding.FragmentCatBinding
import com.example.task5.ui.main.viewmodel.CatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okio.IOException

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            viewModel.catFlow.value?.let { cat ->
                try {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.downloadImage(cat)
                    }
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                } catch (_: IOException) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
