package com.gentledevs.unsplashapp.datasource

import com.gentledevs.unsplashapp.api.ApiInterface
import com.gentledevs.unsplashapp.api.SearchResponse
import com.gentledevs.unsplashapp.extentions.toImageItem
import com.gentledevs.unsplashapp.list.ImageItem
import retrofit2.Call
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

/**
 * Created by Taras Smakula on 2018-04-03.
 */
class ImageListDataSource(private val apiInterface: ApiInterface) {

    private var currentPage = 1
    private var runningCall: Call<SearchResponse>? = null

    private suspend fun searchPhotos(query: String, currentPage: Int): Result<List<ImageItem>> {
        if (runningCall != null) return Result.Exception(Throwable("call already running"))

        val call = apiInterface.searchPhotos(query, currentPage)
        runningCall = call
        val result = call.awaitResult()
        return when (result) {
            is Result.Ok -> {
                runningCall = null
                Result.Ok(
                        result.value.results.map { it.toImageItem() },
                        result.response
                )
            }
            is Result.Error -> result
            is Result.Exception -> result
        }
    }


    suspend fun loadNextPhotos(query: String): Result<List<ImageItem>> {
        if (runningCall != null) return Result.Exception(Throwable("call already running"))

        currentPage++

        return searchPhotos(query, currentPage)
    }

    suspend fun photosFor(query: String): Result<List<ImageItem>> {
        runningCall?.cancel()
        runningCall = null

        currentPage = 1

        return searchPhotos(query, currentPage)
    }

}