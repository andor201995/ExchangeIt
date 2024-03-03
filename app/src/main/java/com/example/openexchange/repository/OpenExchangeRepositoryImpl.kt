package com.example.openexchange.repository

import com.example.openexchange.repository.currency.CurrencyDataSource
import com.example.openexchange.repository.rate.RateDataSource
import com.example.openexchange.repository.timestamp.TimeStampDataSource
import com.example.openexchange.usecase.model.Currency
import com.example.openexchange.usecase.model.Rate
import javax.inject.Inject

class OpenExchangeRepositoryImpl @Inject constructor(
    private val rateDataSource: RateDataSource,
    private val currencyDataSource: CurrencyDataSource,
    private val timeStampDataSource: TimeStampDataSource
) : OpenExchangeRepository {
    override suspend fun removeAllRates() = rateDataSource.removeAll()

    override suspend fun getRates(baseCurrency: String): List<Rate> =
        rateDataSource.get(baseCurrency)

    override suspend fun addRates(values: List<Rate>) = rateDataSource.addAll(values)

    override suspend fun addCurrency(values: List<Currency>) = currencyDataSource.addAll(values)

    override suspend fun getCurrency(): List<Currency> = currencyDataSource.getAll()

    override suspend fun removeAllCurrency() = currencyDataSource.removeAll()

    override suspend fun getLastFetchTimeStamp(): Long = timeStampDataSource.getLastUpdate()

    override suspend fun setLastFetchTimeStamp(value: Long) =
        timeStampDataSource.setLastUpdate(value)

    override suspend fun getCurrentTimeStamp(): Long = timeStampDataSource.getCurrent()
}