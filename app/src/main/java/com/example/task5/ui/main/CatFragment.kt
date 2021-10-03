package com.example.task5.ui.main

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.task5.R
import com.example.task5.api.data.Cat
import com.example.task5.databinding.FragmentCatBinding
import com.example.task5.di.ServiceLocator
import com.example.task5.ui.main.viewmodel.CatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.*
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

        binding.fab.setOnClickListener {
            viewModel.catFlow.value?.let { cat ->
                downloadImage(cat)
            }
        }

        return binding.root
    }

    private fun downloadImage(cat: Cat) {
        val context = ServiceLocator.locate<Context>()// TODO вынести
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url(cat.url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.byteStream()
                if (data != null) {
                    try {
                        val fileName = getFileName(cat.url)
                        val fileMimeType = getFileMimeType(cat.url)
                        val fileMediaStoreUri = run {
                            val imageCollection =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                                } else {
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                }
                            val image = ContentValues().apply {
                                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                                put(MediaStore.Images.Media.MIME_TYPE, fileMimeType)
                            }
                            requireActivity().contentResolver.insert(imageCollection, image)
                        } ?: throw IOException()

                        context.contentResolver.openOutputStream(fileMediaStoreUri).use { outputStream ->
                            outputStream?.write(data.readBytes())
                        }

                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getFileName(url: String): String {
        return url
            .substringAfterLast("/")
            .substringBefore(".")
    }

    private fun getFileMimeType(url: String): String {
        val mimeType = MimeTypeMap.getFileExtensionFromUrl(url)?.let { extension ->
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return mimeType ?: throw IOException()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
