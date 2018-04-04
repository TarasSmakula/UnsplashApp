package com.gentledevs.unsplashapp.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gentledevs.unsplashapp.datasource.ImageListDataSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import ru.gildor.coroutines.retrofit.Result

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class PhotoListViewModel(private val dataSource: ImageListDataSource) : ViewModel() {

    private val _images = MutableLiveData<List<ImageItem>>()
    val images: LiveData<List<ImageItem>> = _images
    private var imagesList: List<ImageItem> = listOf()

    private val _launchFullPhotoEvent = MutableLiveData<Pair<ImageItem, Int>>()
    val launchFullPhotoEvent: LiveData<Pair<ImageItem, Int>> = _launchFullPhotoEvent

    private var queryForSearch = ""


    fun onImageSelected(position: Int) {
        _launchFullPhotoEvent.value = imagesList[position] to position
        _launchFullPhotoEvent.value = null
    }


    fun onLoadMorePhotos() {
        async(UI) {
            val photos = dataSource.loadNextPhotos(queryForSearch)
            if (photos is Result.Ok) {
                imagesList += photos.value
                _images.value = imagesList
            }
        }
    }

    fun searForPhotos(query: String = "unsplash") {
        queryForSearch = query
        async(UI) {
            val photos = dataSource.photosFor(query)
            if (photos is Result.Ok) {
                _images.value = photos.value
                imagesList = photos.value
            }
        }
    }

}