package com.gentledevs.unsplashapp

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.gentledevs.unsplashapp.injection.applicationModules
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.startKoin

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, applicationModules)
        initPicasso()
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
                .memoryCache(LruCache(calculateMemoryCacheSize()))
                .build()
        Picasso.setSingletonInstance(picasso)
    }

    private fun calculateMemoryCacheSize(): Int {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryClass = am.memoryClass
        return 1024 * 1024 * memoryClass / 4
    }
}