package com.example.openexchange.repository

import com.example.openexchange.usecase.model.Currency
import com.example.openexchange.usecase.model.Rate

interface OpenExchangeRepository {
    suspend fun removeAllRates()
    suspend fun getRates(baseCurrency: String): List<Rate>
    suspend fun addRates(values: List<Rate>)
    suspend fun addCurrency(values: List<Currency>)
    suspend fun getCurrency(): List<Currency>
    suspend fun removeAllCurrency()
    suspend fun getLastFetchTimeStamp(): Long
    suspend fun setLastFetchTimeStamp(value: Long)
    suspend fun getCurrentTimeStamp(): Long
}