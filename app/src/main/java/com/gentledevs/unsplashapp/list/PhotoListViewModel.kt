package com.gentledevs.unsplashapp.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gentledevs.unsplashapp.datasource.ImageListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class PhotoListViewModel(private val dataSource: ImageListDataSource) : ViewModel() {

    private val _images = MutableLiveData<List<ImageItem>>()
    val images: LiveData<List<ImageItem>> = _images
    private var imagesList: List<ImageItem> = listOf()

    private val _launchFullPhotoEvent = MutableLiveData<Pair<ImageItem, Int>?>()
    val launchFullPhotoEvent: MutableLiveData<Pair<ImageItem, Int>?> = _launchFullPhotoEvent

    private var queryForSearch = ""


    fun onImageSelected(position: Int) {
        _launchFullPhotoEvent.value = imagesList[position] to position
        _launchFullPhotoEvent.value = null
    }


    fun onLoadMorePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.loadNextPhotos(queryForSearch).fold(
                onSuccess = {
                    imagesList = imagesList + it
                    _images.postValue(imagesList)
                },
                onFailure = {}
            )
        }
    }

    fun searForPhotos(query: String = "unsplash") {
        queryForSearch = query
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.photosFor(queryForSearch).fold(
                onSuccess = {
                    imagesList = it
                    _images.postValue(imagesList)
                },
                onFailure = {}
            )
        }
    }

}