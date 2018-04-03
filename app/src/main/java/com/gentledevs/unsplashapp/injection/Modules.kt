package com.gentledevs.unsplashapp.injection

import com.gentledevs.unsplashapp.BuildConfig
import com.gentledevs.unsplashapp.api.ApiInterface
import com.gentledevs.unsplashapp.api.ClientInterceptor
import com.gentledevs.unsplashapp.datasource.ImageListDataSource
import com.gentledevs.unsplashapp.list.PhotoListViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Taras Smakula on 2018-04-02.
 */

private const val BASE_URL = "URL"
private const val GSON = "GSON"

private val apiModule = applicationContext {
    bean(BASE_URL) { BuildConfig.BASE_URL }
    bean(GSON) {
        GsonConverterFactory.create(GsonBuilder().create()) as Converter.Factory
    }
    bean {
        OkHttpClient.Builder().apply {
            addInterceptor(get<ClientInterceptor>())
        }.build()
    }
    bean { ClientInterceptor() }
    bean {
        Retrofit.Builder()
                .baseUrl(get<String>(BASE_URL))
                .addConverterFactory(get(GSON))
                .client(get())
                .build()
    }

    factory { get<Retrofit>().create(ApiInterface::class.java) }

}

private val uiModule = applicationContext {
    viewModel { PhotoListViewModel(get()) }
}


private val dataModule = applicationContext {
    factory { ImageListDataSource(get()) }
}

val applicationModules = listOf(uiModule, apiModule, dataModule)