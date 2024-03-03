package com.example.openexchange.network

import com.example.openexchange.usecase.model.Currency
import com.example.openexchange.usecase.model.Rate

interface OpenExchangeEndpoint {
    suspend fun fetchExchangeRates(baseCurrencyCode: String): List<Rate>
    suspend fun fetchCurrency(): List<Currency>
}