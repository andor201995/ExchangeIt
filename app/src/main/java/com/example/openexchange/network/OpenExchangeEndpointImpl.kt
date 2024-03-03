package com.example.openexchange.network


import com.example.openexchange.network.model.ExchangeSchema
import com.example.openexchange.usecase.model.Currency
import com.example.openexchange.usecase.model.Rate
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class OpenExchangeEndpointImpl @Inject constructor(private val api: OpenExchangeApi) :
    OpenExchangeEndpoint {
    override suspend fun fetchExchangeRates(baseCurrencyCode: String): List<Rate> {
        return api.fetchExchangeRates(baseCurrencyCode).toModel()
    }

    override suspend fun fetchCurrency(): List<Currency> {
        return api.fetchCurrencies().toModel()
    }
}

@VisibleForTesting
internal fun Map<String, String>.toModel(): List<Currency> =
    this.map { Currency(it.key, it.value) }

@VisibleForTesting
internal fun ExchangeSchema.toModel() = this.rates?.map {
    Rate(it.key, it.value, this.baseCurrencyCode ?: "")
} ?: emptyList()


