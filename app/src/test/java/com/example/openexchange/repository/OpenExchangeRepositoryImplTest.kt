package com.example.openexchange.repository

import com.example.openexchange.repository.currency.CurrencyDataSource
import com.example.openexchange.repository.rate.RateDataSource
import com.example.openexchange.repository.timestamp.TimeStampDataSource
import com.example.openexchange.usecase.model.Currency
import com.example.openexchange.usecase.model.Rate
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OpenExchangeRepositoryImplTest {


    @Mock
    lateinit var rateDataSource: RateDataSource

    @Mock
    lateinit var currencyDataSource: CurrencyDataSource

    @Mock
    lateinit var timeStampDataSource: TimeStampDataSource

    @InjectMocks
    lateinit var systemUT: OpenExchangeRepositoryImpl


    @Test
    fun `test removeAllRates`() = runTest {
        systemUT.removeAllRates()
        verify(rateDataSource).removeAll()
    }

    @Test
    fun `test getRates`() = runTest {
        val input = "USD"
        systemUT.getRates(input)
        verify(rateDataSource).get(input)
    }

    @Test
    fun `test addRates`() = runTest {
        val input = emptyList<Rate>()
        systemUT.addRates(input)
        verify(rateDataSource).addAll(input)
    }

    @Test
    fun ` test addCurrency`() = runTest {
        val input = emptyList<Currency>()
        systemUT.addCurrency(input)
        verify(currencyDataSource).addAll(input)
    }

    @Test
    fun ` test getCurrency`() = runTest {
        systemUT.getCurrency()
        verify(currencyDataSource).getAll()
    }

    @Test
    fun ` test removeAllCurrency`() = runTest {
        systemUT.removeAllCurrency()
        verify(currencyDataSource).removeAll()
    }

    @Test
    fun ` test getLastFetchTimeStamp`() = runTest {
        systemUT.getLastFetchTimeStamp()
        verify(timeStampDataSource).getLastUpdate()
    }

    @Test
    fun ` test setLastFetchTimeStamp`() = runTest {
        systemUT.setLastFetchTimeStamp(0L)
        verify(timeStampDataSource).setLastUpdate(0L)
    }

    @Test
    fun ` test getCurrentTimeStamp`() = runTest {
        systemUT.getCurrentTimeStamp()
        verify(timeStampDataSource).getCurrent()
    }

}