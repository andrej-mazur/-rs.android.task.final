package com.example.task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task5.api.data.Cat
import com.example.task5.databinding.FragmentCatListBinding
import com.example.task5.ui.main.viewmodel.CatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null

    private val binding get() = requireNotNull(_binding)

    private val viewModel: CatViewModel by activityViewModels()

    private var searchJob: Job? = null

    private val catAdapter = CatAdapter(object : CatAdapterListener {

        override fun onClick(cat: Cat) {
            viewModel.updateCatFlow(cat)
            val direction = CatListFragmentDirections.actionListToSingle()
            findNavController().navigate(direction)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)

        with(binding) {
            catList.adapter = catAdapter
        }

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.catPageFlow.collectLatest {
                catAdapter.submitData(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
