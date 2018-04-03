package com.gentledevs.unsplashapp.api

import com.gentledevs.unsplashapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Taras Smakula on 2018-04-03.
 */
const val HEADER_NAME = "Authorization"

class ClientInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestToProceed =
            chain.request().newBuilder()
                    .header(HEADER_NAME, "Client-ID ${BuildConfig.Client_ID}")
                    .build()

        return chain.proceed(requestToProceed)
    }

}