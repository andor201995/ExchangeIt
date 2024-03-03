package com.example.openexchange.repository.rate

import com.example.openexchange.usecase.model.Rate

interface RateDataSource {
    suspend fun addAll(values: List<Rate>)
    suspend fun removeAll()
    suspend fun get(baseCurrency: String): List<Rate>
    suspend fun getAllCurrencyCode(): List<String>
}