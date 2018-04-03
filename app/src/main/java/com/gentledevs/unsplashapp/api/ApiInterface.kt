package com.gentledevs.unsplashapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Taras Smakula on 2018-04-03.
 */
interface ApiInterface {

    @GET("/search/photos/{query}/{page}/{per_page}/{orientation}")
    fun searchPhotos(@Path("query") query: String, @Path("page") page: Int, @Path("per_page") perPage: Int = 20, @Path("orientation") orientation: String = "portrait"): Call<SearchResponse>

}