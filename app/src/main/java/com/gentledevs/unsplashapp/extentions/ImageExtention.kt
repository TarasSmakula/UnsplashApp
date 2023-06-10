package com.gentledevs.unsplashapp.extentions

import android.content.Context
import androidx.annotation.DimenRes
import com.gentledevs.unsplashapp.list.ImageItem

/**
 * Created by Taras Smakula on 2018-04-04.
 */
fun ImageItem.calculateHeight(withToFit: Int): Int {
    val ratio = width / height.toFloat()
    val height = withToFit / ratio
    return height.toInt()
}

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)