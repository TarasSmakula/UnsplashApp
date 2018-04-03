package com.gentledevs.unsplashapp.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.gentledevs.unsplashapp.datasource.ImageListDataSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import ru.gildor.coroutines.retrofit.Result
import java.lang.Exception

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class PhotoListViewModel(private val dataSource: ImageListDataSource) : ViewModel() {

    private val _images = MutableLiveData<List<ImageItem>>()
    val images: LiveData<List<ImageItem>> = _images
    private var imagesList: MutableList<ImageItem> = mutableListOf()
    private var currentPage = 1
    private var queryForSearch = ""


    fun onImageSelected(position: Int) {
        Log.d("mylog", "selected")
    }

    fun onLoadMorePhotos() {
        currentPage++
        async(UI) {
            val photos = dataSource.searchPhotos(queryForSearch, currentPage)
            if (photos is Result.Ok) {
                _images.value = photos.value
                imagesList = photos.value.toMutableList()
            }
        }
    }

    fun searForPhotos(query: String) {
        currentPage = 1
        queryForSearch = query
        async(UI) {
            try {
                val photos = dataSource.searchPhotos(query, currentPage)
                if (photos is Result.Ok) {
                    _images.value = imagesList.apply { imagesList.addAll(photos.value) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}