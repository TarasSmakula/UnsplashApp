package com.gentledevs.unsplashapp.extentions

import com.gentledevs.unsplashapp.api.ImageResultItem
import com.gentledevs.unsplashapp.list.ImageItem

/**
 * Created by Taras Smakula on 2018-04-03.
 */
fun ImageResultItem.toImageItem() = ImageItem(id, urls.thumbsUrl, urls.fullUrl)