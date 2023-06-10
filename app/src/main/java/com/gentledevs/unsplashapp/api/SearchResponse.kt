package com.gentledevs.unsplashapp.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Taras Smakula on 2018-04-03.
 */
data class SearchResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: ArrayList<ImageResultItem>
)
