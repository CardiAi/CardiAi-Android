package com.codlin.cardiai

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CardAiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        plantTimberTree()
    }

    private fun plantTimberTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(ReportingTree())
        }
    }
}