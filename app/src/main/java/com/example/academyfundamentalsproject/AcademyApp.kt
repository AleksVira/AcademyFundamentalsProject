package com.example.academyfundamentalsproject

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class AcademyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}