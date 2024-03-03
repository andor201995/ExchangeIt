package com.example.openexchange.ui.viewModel

import com.example.openexchange.helper.MainDispatcherRule
import com.example.openexchange.ui.model.CurrencyUiModel
import com.example.openexchange.ui.model.InputUiModel
import com.example.openexchange.ui.model.RateUiModel
import com.example.openexchange.ui.model.ScreenUiModel
import com.example.openexchange.usecase.GetAvailableCurrency
import com.example.openexchange.usecase.GetExchangeRates
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.firstValue
import com.nhaarman.mockitokotlin2.lastValue
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ExchangeScreenViewModelTest {
    @Mock
    private lateinit var rateUseCase: GetExchangeRates

    @Mock
    private lateinit var currency: GetAvailableCurrency

    @Captor
    lateinit var stringArgCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var doubleArgCaptor: ArgumentCaptor<Double>

    private lateinit var systemUT: ExchangeScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        runBlocking {
            whenever(rateUseCase.invoke("USD", 1.0)).thenReturn(RateUiModel())
            whenever(currency.invoke()).thenReturn(CurrencyUiModel())
            systemUT = ExchangeScreenViewModel(rateUseCase, currency)
        }
    }

    @Test
    fun `test onCurrencySelected`() = runTest {
        whenever(rateUseCase.invoke("INR", 1.0)).thenReturn(RateUiModel())

        systemUT.onCurrencySelected("INR")

        advanceUntilIdle()
        val state = systemUT.state.value
        verify(rateUseCase, times(2)).invoke(capture(stringArgCaptor), any())
        Assert.assertEquals(ScreenUiModel(input = InputUiModel("INR", 1.0)), state)
        assert(stringArgCaptor.allValues.first() == "USD")
        assert(stringArgCaptor.allValues[1] == "INR")
    }

    @Test
    fun `test onAmountChanged`() = runTest {
        whenever(rateUseCase.invoke("USD", 2.0)).thenReturn(RateUiModel())
        systemUT.onAmountChanged("2.0")

        advanceUntilIdle()
        val state = systemUT.state.value
        verify(rateUseCase, times(2)).invoke(capture(stringArgCaptor), any())
        Assert.assertEquals(ScreenUiModel(input = InputUiModel("USD", 2.0)), state)
        assert(stringArgCaptor.allValues.first() == "USD")
        assert(stringArgCaptor.allValues[1] == "USD")
    }

    @Test
    fun `test onAmountChanged debounce cancel old job`() = runTest {
        whenever(rateUseCase.invoke("USD", 3.0)).thenReturn(RateUiModel())
        systemUT.onAmountChanged("2.1")
        advanceTimeBy(100)
        systemUT.onAmountChanged("2.2")
        advanceTimeBy(100)
        systemUT.onAmountChanged("2.3")
        advanceTimeBy(100)
        systemUT.onAmountChanged("3.0")
        advanceUntilIdle()
        verify(rateUseCase, atLeastOnce()).invoke(capture(stringArgCaptor), capture(doubleArgCaptor))
        Assert.assertEquals(1.0, doubleArgCaptor.firstValue,0.0)
        Assert.assertEquals(3.0, doubleArgCaptor.lastValue,0.0)
        assert(!doubleArgCaptor.allValues.contains(2.1))
        assert(!doubleArgCaptor.allValues.contains(2.2))
        assert(!doubleArgCaptor.allValues.contains(2.3))
    }

    @Test
    fun `test loadData`() = runTest {
        systemUT.loadData()
        val state = systemUT.state.value
        verify(rateUseCase).invoke("USD", 1.0)
        verify(currency).invoke()
        Assert.assertEquals(ScreenUiModel(), state)
    }

}