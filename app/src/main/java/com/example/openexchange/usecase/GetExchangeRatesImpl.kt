package com.example.openexchange.usecase

import com.example.openexchange.network.OpenExchangeEndpoint
import com.example.openexchange.repository.OpenExchangeRepository
import com.example.openexchange.ui.model.RateUiModel
import com.example.openexchange.usecase.model.Rate
import javax.inject.Inject

class GetExchangeRatesImpl @Inject constructor(
    private val repository: OpenExchangeRepository,
    private val endpoint: OpenExchangeEndpoint,
    private val outdated: DataOutdated
) : GetExchangeRates {
    override suspend fun invoke(
        baseCurrencyCode: String,
        value: Double
    ): RateUiModel {
        val rates = if (!outdated()) {
            repository.getRates(DEFAULT_BASE_CURRENCY)
        } else {
            fetchRatesFromNetwork()
        }

        val updatedRates = convertRatesToBase(rates, baseCurrencyCode, value)
        return RateUiModel(updatedRates)
    }

    private fun convertRatesToBase(
        rates: List<Rate>,
        baseCurrencyCode: String,
        value: Double
    ): List<Rate> {
        val baseRate =
            rates.find { it.currencyCode == baseCurrencyCode && it.value > 0 } ?: return emptyList()
        return baseRate.let { base ->
            rates.map {
                it.copy(
                    baseCurrencyCode = base.currencyCode,
                    value = (it.value / base.value) * value
                )
            }
        }
    }

    private suspend fun fetchRatesFromNetwork(): List<Rate> = try {
        val rateList = endpoint.fetchExchangeRates(DEFAULT_BASE_CURRENCY)
        if (rateList.isNotEmpty()) {
            repository.setLastFetchTimeStamp(repository.getCurrentTimeStamp())
            repository.removeAllRates()
            repository.addRates(rateList)
        }
        rateList
    } catch (e: Exception) {
        repository.getRates(DEFAULT_BASE_CURRENCY)
    }


    companion object {
        const val DEFAULT_BASE_CURRENCY = "USD"
    }
}
