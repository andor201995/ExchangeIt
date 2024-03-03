package com.example.openexchange.repository.timestamp

interface TimeStampDataSource {
    suspend fun getLastUpdate(): Long
    suspend fun setLastUpdate(value: Long)
    suspend fun getCurrent(): Long
}