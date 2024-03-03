package com.example.openexchange.usecase

import com.example.openexchange.network.OpenExchangeEndpoint
import com.example.openexchange.repository.OpenExchangeRepository
import com.example.openexchange.ui.model.CurrencyUiModel
import com.example.openexchange.usecase.model.Currency
import javax.inject.Inject

class GetAvailableCurrencyImpl @Inject constructor(
    private val endpoint: OpenExchangeEndpoint,
    private val repository: OpenExchangeRepository
) : GetAvailableCurrency {
    override suspend fun invoke(): CurrencyUiModel {
        val currency = repository.getCurrency().ifEmpty { fetchFromNetwork() }
        return CurrencyUiModel(currency)
    }

    private suspend fun fetchFromNetwork(): List<Currency> = try {
        val currencies = endpoint.fetchCurrency()
        if (currencies.isNotEmpty()) {
            repository.removeAllCurrency()
            repository.addCurrency(currencies)
        }
        currencies
    } catch (e: Exception) {
        emptyList()
    }
}