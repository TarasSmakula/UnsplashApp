package com.gentledevs.unsplashapp.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Taras Smakula on 2018-04-03.
 */
data class ImageResultItem(
        @SerializedName("id") val id: String,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("urls") val urls: ImageUrls
)