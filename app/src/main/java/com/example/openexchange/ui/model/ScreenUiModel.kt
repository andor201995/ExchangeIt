package com.example.openexchange.ui.model

data class ScreenUiModel(
    val input: InputUiModel = InputUiModel(),
    val currency: CurrencyUiModel = CurrencyUiModel(),
    val rates: RateUiModel = RateUiModel()
)