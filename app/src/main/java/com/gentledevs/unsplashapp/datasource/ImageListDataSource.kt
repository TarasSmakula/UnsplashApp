package com.gentledevs.unsplashapp.datasource

import com.gentledevs.unsplashapp.api.ApiInterface
import com.gentledevs.unsplashapp.extentions.toImageItem
import com.gentledevs.unsplashapp.list.ImageItem

/**
 * Created by Taras Smakula on 2018-04-03.
 */
class ImageListDataSource(private val apiInterface: ApiInterface) {

    private var currentPage = 1

    private suspend fun searchPhotos(query: String, currentPage: Int): Result<List<ImageItem>> {
        val response = apiInterface.searchPhotos(query, currentPage)
        return if (response.isSuccessful) {
            Result.success(response.body()?.results?.map { it.toImageItem() } ?: emptyList())
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    suspend fun loadNextPhotos(query: String): Result<List<ImageItem>> {
        currentPage++
        return searchPhotos(query, currentPage)
    }

    suspend fun photosFor(query: String): Result<List<ImageItem>> {
        currentPage = 1
        return searchPhotos(query, currentPage)
    }

}