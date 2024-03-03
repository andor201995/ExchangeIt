package com.example.openexchange.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeSchema(
    @SerialName("timestamp") val timeStamp: Long? = null,
    @SerialName("rates") val rates: Map<String, Double>? = null,
    @SerialName("base") val baseCurrencyCode: String? = null
) {
    companion object {
        val EMPTY = ExchangeSchema(
            timeStamp = 0L,
            rates = emptyMap(),
            baseCurrencyCode = ""
        )
    }
}
