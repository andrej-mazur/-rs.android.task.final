package com.example.task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.task5.databinding.FragmentCatListBinding
import com.example.task5.ui.main.viewmodel.CatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null

    private val binding get() = requireNotNull(_binding)

    private var searchJob: Job? = null

    private val catAdapter = CatAdapter()

    private val catViewModel: CatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)

        with(binding) {
            catList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            catList.adapter = catAdapter
        }

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            catViewModel.searchImages().collectLatest {
                catAdapter.submitData(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun create() = CatListFragment()
    }
}
