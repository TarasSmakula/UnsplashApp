package com.gentledevs.unsplashapp

import android.app.Application
import com.gentledevs.unsplashapp.injection.applicationModules
import org.koin.android.ext.android.startKoin

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, applicationModules)
    }
}