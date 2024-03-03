package com.example.openexchange.usecase

import com.example.openexchange.network.OpenExchangeEndpoint
import com.example.openexchange.repository.OpenExchangeRepository
import com.example.openexchange.ui.model.RateUiModel
import com.example.openexchange.usecase.model.Rate
import com.nhaarman.mockitokotlin2.any
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
class GetExchangeRatesImplTest {

    @Mock
    private lateinit var repository: OpenExchangeRepository

    @Mock
    private lateinit var endpoint: OpenExchangeEndpoint

    @Mock
    private lateinit var outdated: DataOutdated

    @InjectMocks
    private lateinit var systemUT: GetExchangeRatesImpl

    @Test
    fun `fetch data from repo when outdated false`() = runTest {
        whenever(outdated.invoke()).thenReturn(false)
        whenever(repository.getRates("USD")).thenReturn(listOf())

        val result = systemUT("THB", 1.0)

        Assert.assertEquals(RateUiModel(emptyList()), result)
        verify(repository).getRates("USD")
    }

    @Test
    fun `fetch data from network when outdated true empty result`() = runTest {
        whenever(outdated.invoke()).thenReturn(true)
        whenever(endpoint.fetchExchangeRates("USD")).thenReturn(listOf())

        val result = systemUT("THB", 1.0)

        Assert.assertEquals(RateUiModel(emptyList()), result)
        verify(endpoint).fetchExchangeRates("USD")
        verify(repository, never()).setLastFetchTimeStamp(any())
        verify(repository, never()).getCurrentTimeStamp()
        verify(repository, never()).addRates(any())
    }

    @Test
    fun `fetch data from network when outdated true`() = runTest {
        whenever(outdated.invoke()).thenReturn(true)
        whenever(endpoint.fetchExchangeRates("USD")).thenReturn(
            listOf(
                Rate("INR", 80.0, "USD"),
                Rate("THB", 40.0, "USD"),
                Rate("XYZ", 0.0, "USD"),
            )
        )

        val expectedResult = RateUiModel(
            listOf(
                Rate("INR", 1.0, "INR"),
                Rate("THB", 0.5, "INR"),
                Rate("XYZ", 0.0, "INR"),
            )
        )
        whenever(repository.getCurrentTimeStamp()).thenReturn(1L)

        val result = systemUT("INR", 1.0)

        Assert.assertEquals(expectedResult, result)
        verify(endpoint).fetchExchangeRates("USD")
        verify(repository).setLastFetchTimeStamp(any())
        verify(repository).getCurrentTimeStamp()
        verify(repository).addRates(any())
    }

    @Test
    fun `fetch network failure when outdated true`() = runTest {
        whenever(outdated.invoke()).thenReturn(true)
        whenever(endpoint.fetchExchangeRates("USD")).thenThrow(IllegalStateException())
        whenever(repository.getRates("USD")).thenReturn(
            listOf(
                Rate("INR", 80.0, "USD"),
                Rate("THB", 40.0, "USD"),
                Rate("XYZ", 0.0, "USD"),
            )
        )

        val expectedResult = RateUiModel(
            listOf(
                Rate("INR", 1.0, "INR"),
                Rate("THB", 0.5, "INR"),
                Rate("XYZ", 0.0, "INR"),
            )
        )

        val result = systemUT("INR", 1.0)

        Assert.assertEquals(expectedResult, result)
        verify(endpoint).fetchExchangeRates("USD")
        verify(repository).getRates("USD")
        verify(repository, never()).setLastFetchTimeStamp(any())
        verify(repository, never()).getCurrentTimeStamp()
        verify(repository, never()).addRates(any())
    }

    @Test
    fun `map data from repo when one currency 0 value`() = runTest {
        whenever(outdated.invoke()).thenReturn(false)
        whenever(repository.getRates("USD")).thenReturn(
            listOf(
                Rate("INR", 80.0, "USD"),
                Rate("THB", 40.0, "USD"),
                Rate("XYZ", 0.0, "USD"),
            )
        )
        val expectedResult = RateUiModel(
            listOf(
                Rate("INR", 1.0, "INR"),
                Rate("THB", 0.5, "INR"),
                Rate("XYZ", 0.0, "INR"),
            )
        )

        val result = systemUT("INR", 1.0)

        Assert.assertEquals(expectedResult, result)
        verify(repository).getRates("USD")
    }

    @Test
    fun `map data from repo when base currency 0 value`() = runTest {
        whenever(outdated.invoke()).thenReturn(false)
        whenever(repository.getRates("USD")).thenReturn(
            listOf(
                Rate("INR", 0.0, "USD"),
                Rate("THB", 40.0, "USD"),
                Rate("XYZ", 0.0, "USD"),
            )
        )

        val result = systemUT("INR", 1.0)

        Assert.assertEquals(RateUiModel(emptyList()), result)
        verify(repository).getRates("USD")
    }

    @Test
    fun `map data from repo when base currency not available`() = runTest {
        whenever(outdated.invoke()).thenReturn(false)
        whenever(repository.getRates("USD")).thenReturn(
            listOf(
                Rate("THB", 40.0, "USD"),
                Rate("XYZ", 0.0, "USD"),
            )
        )

        val result = systemUT("INR", 1.0)

        Assert.assertEquals(RateUiModel(emptyList()), result)
        verify(repository).getRates("USD")
    }
}