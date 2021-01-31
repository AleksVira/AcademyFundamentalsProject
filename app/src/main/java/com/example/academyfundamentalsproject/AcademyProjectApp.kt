package com.example.academyfundamentalsproject

import android.app.Application
import timber.log.Timber

import timber.log.Timber.DebugTree

class AcademyProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}