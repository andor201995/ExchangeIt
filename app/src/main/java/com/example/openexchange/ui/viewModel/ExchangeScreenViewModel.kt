package com.example.openexchange.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openexchange.usecase.GetAvailableCurrency
import com.example.openexchange.usecase.GetExchangeRates

import com.example.openexchange.ui.model.ScreenUiModel
import com.example.openexchange.ui.model.InputUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

class ExchangeScreenViewModel @Inject constructor(
    private val rateUseCase: GetExchangeRates,
    private val currencyUseCase: GetAvailableCurrency
) : ViewModel() {
    private val uiState = MutableStateFlow(ScreenUiModel())
    private var getRatesJob: Job? = null
    val state: StateFlow<ScreenUiModel> get() = uiState

    init {
        loadData()
    }

    @VisibleForTesting
    fun loadData() {
        getCurrencies()
        getRatesJob = getRates(uiState.value.input)
    }

    fun onCurrencySelected(code: String) = viewModelScope.launch {
        getRatesJob?.cancel()
        uiState.value =
            uiState.value.copy(input = uiState.value.input.copy(selectedCurrency = code))
        getRatesJob = getRates(uiState.value.input)
    }

    fun onAmountChanged(amount: String) = viewModelScope.launch {
        getRatesJob?.cancel()
        uiState.value = uiState.value.copy(
            input = uiState.value.input.copy(
                amount = amount.toDoubleOrNull() ?: 0.0
            )
        )
        // debounce
        delay(2000)
        getRatesJob = getRates(uiState.value.input)
    }

    private fun getCurrencies() = viewModelScope.launch {
        currencyUseCase().let {
            uiState.value = uiState.value.copy(currency = it)
        }
    }

    private fun getRates(input: InputUiModel) = viewModelScope.launch {
        rateUseCase(
            input.selectedCurrency,
            input.amount
        ).let {
            uiState.value = uiState.value.copy(rates = it)
        }
    }
}