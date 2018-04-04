package com.gentledevs.unsplashapp.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Taras Smakula on 2018-04-02.
 */
@Parcelize
data class ImageItem(val id: String, val width: Int, val height: Int, val thumbImageUrl: String, val fullImageUrl: String):Parcelable