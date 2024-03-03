package com.example.openexchange.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.json.Json

@HiltAndroidApp
class OpenExchangeApplication: Application() {
    override fun onCreate() {
        Json { ignoreUnknownKeys = true }
        super.onCreate()
    }
}