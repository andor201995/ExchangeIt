package com.example.openexchange.usecase

import com.example.openexchange.network.OpenExchangeEndpoint
import com.example.openexchange.repository.OpenExchangeRepository
import com.example.openexchange.ui.model.CurrencyUiModel
import com.example.openexchange.usecase.model.Currency
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAvailableCurrencyImplTest {
    @Mock
    private lateinit var repository: OpenExchangeRepository

    @Mock
    private lateinit var endpoint: OpenExchangeEndpoint

    @InjectMocks
    private lateinit var systemUT: GetAvailableCurrencyImpl

    @Test
    fun `fetch data from repo if cached in db`() = runTest {
        val value = listOf(Currency("USD", "US Dollar"))
        whenever(repository.getCurrency()).thenReturn(value)
        val result = systemUT()
        Assert.assertEquals(CurrencyUiModel(value), result)
        verify(repository).getCurrency()
        verify(endpoint, never()).fetchCurrency()
    }

    @Test
    fun `fetch data from network if cached empty`() = runTest {
        val value = listOf(Currency("USD", "US Dollar"))
        whenever(repository.getCurrency()).thenReturn(emptyList())
        whenever(endpoint.fetchCurrency()).thenReturn(listOf(Currency("USD", "US Dollar")))
        val result = systemUT()
        Assert.assertEquals(CurrencyUiModel(value), result)
        verify(repository).getCurrency()
        verify(repository).removeAllCurrency()
        verify(repository).addCurrency(value)
        verify(endpoint).fetchCurrency()
    }

    @Test
    fun `fetch data from network empty`() = runTest {
        val value = listOf(Currency("USD", "US Dollar"))
        whenever(repository.getCurrency()).thenReturn(emptyList())
        whenever(endpoint.fetchCurrency()).thenReturn(emptyList())
        val result = systemUT()
        Assert.assertEquals(CurrencyUiModel(emptyList()), result)
        verify(repository).getCurrency()
        verify(repository, never()).removeAllCurrency()
        verify(repository, never()).addCurrency(value)
        verify(endpoint).fetchCurrency()
    }

    @Test
    fun `fetch data from network throw error`() = runTest {
        val value = listOf(Currency("USD", "US Dollar"))
        whenever(repository.getCurrency()).thenReturn(emptyList())
        whenever(endpoint.fetchCurrency()).thenThrow(IllegalStateException())
        val result = systemUT()
        Assert.assertEquals(CurrencyUiModel(emptyList()), result)
        verify(repository).getCurrency()
        verify(repository, never()).removeAllCurrency()
        verify(repository, never()).addCurrency(value)
        verify(endpoint).fetchCurrency()
    }
}