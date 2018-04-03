package com.gentledevs.unsplashapp.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Taras Smakula on 2018-04-03.
 */
data class ImageUrls(
        @SerializedName("raw") val rawUrl: String,
        @SerializedName("full") val fullUrl: String,
        @SerializedName("regular") val regularUrl: String,
        @SerializedName("small") val smallUrl: String,
        @SerializedName("thumb") val thumbsUrl: String
)