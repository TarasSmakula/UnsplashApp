package com.gentledevs.unsplashapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Taras Smakula on 2018-04-03.
 */
interface ApiInterface {

    @GET("/search/photos")
    fun searchPhotos(@Query("query") query: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 20, @Query("orientation") orientation: String = "portrait"): Call<SearchResponse>

}