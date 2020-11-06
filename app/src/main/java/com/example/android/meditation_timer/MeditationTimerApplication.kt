package com.example.android.meditation_timer

import android.app.Application
import timber.log.Timber


class MeditationTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}
