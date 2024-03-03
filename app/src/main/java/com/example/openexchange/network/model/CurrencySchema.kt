package com.example.openexchange.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencySchema(
    val currencyCode: String,
    val currencyName: String
)