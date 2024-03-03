package com.example.openexchange.repository.timestamp

import android.content.SharedPreferences
import com.example.openexchange.common.Constants
import javax.inject.Inject
import javax.inject.Named

class TimeStampDataSourceImpl @Inject constructor(
    @Named(Constants.SHARED_PREF_TIMESTAMP) private val sharedPreferences: SharedPreferences
) : TimeStampDataSource {
    override suspend fun getLastUpdate(): Long {
        return sharedPreferences.getLong(KEY_TIMESTAMP, 0L)
    }

    override suspend fun setLastUpdate(value: Long) {
        with(sharedPreferences.edit()) {
            putLong(KEY_TIMESTAMP, value)
            apply()
        }
    }

    override suspend fun getCurrent(): Long {
        return System.currentTimeMillis()
    }

    companion object {
        const val KEY_TIMESTAMP = "last_data_fetch_timestamp"
    }
}