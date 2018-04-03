package com.gentledevs.unsplashapp.datasource

import com.gentledevs.unsplashapp.api.ApiInterface
import com.gentledevs.unsplashapp.extentions.toImageItem
import com.gentledevs.unsplashapp.list.ImageItem
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

/**
 * Created by Taras Smakula on 2018-04-03.
 */
class ImageListDataSource(private val apiInterface: ApiInterface) {

    suspend fun searchPhotos(query: String, currentPage: Int): Result<List<ImageItem>> {
        val result = apiInterface.searchPhotos(query, currentPage).awaitResult()
        return when (result) {
            is Result.Ok -> Result.Ok(
                    result.value.results.map { it.toImageItem() },
                    result.response
            )
            is Result.Error -> result
            is Result.Exception -> result
        }
    }

}