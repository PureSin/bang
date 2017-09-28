package com.standoff.bang.bang.application

import android.app.Application
import android.util.Log

import timber.log.Timber

/**
 * Created by gavra on 9/28/17.
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
