package com.example.task5.ui.main.viewmodel

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.task5.api.data.Cat
import com.example.task5.datasource.CatPagingRepository
import com.example.task5.di.ServiceLocator
import com.example.task5.di.locateLazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class CatViewModel : ViewModel() {

    private val repository: CatPagingRepository by locateLazy()

    private val _catPageFlow = repository.getCatPageFlow()

    val catPageFlow: Flow<PagingData<Cat>> = _catPageFlow.cachedIn(viewModelScope)

    private val _catFlow = MutableStateFlow<Cat?>(null)

    val catFlow: StateFlow<Cat?> = _catFlow.asStateFlow()

    fun updateCatFlow(cat: Cat) {
        _catFlow.tryEmit(cat)
    }

    fun downloadImage(cat: Cat) {
        val context = ServiceLocator.locate<Context>()
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url(cat.url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful || response.body == null) {
                throw IOException()
            }

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
                context.contentResolver.insert(imageCollection, image)
            } ?: throw IOException()

            response.body?.let {
                context.contentResolver.openOutputStream(fileMediaStoreUri).use { outputStream ->
                    outputStream?.write(it.byteStream().readBytes())
                }
            }
        }
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
}
