package com.example.openexchange.usecase

import com.example.openexchange.ui.model.RateUiModel

interface GetExchangeRates {
    suspend operator fun invoke(baseCurrencyCode: String, value: Double): RateUiModel
}
