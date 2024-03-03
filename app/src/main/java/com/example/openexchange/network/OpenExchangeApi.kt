package com.example.openexchange.network

import com.example.openexchange.network.model.ExchangeSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeApi {

    @GET("latest.json")
    suspend fun fetchExchangeRates(
        @Query("base") base: String = "USD"
    ): ExchangeSchema

    @GET("currencies.json")
    suspend fun fetchCurrencies(): Map<String, String>
}