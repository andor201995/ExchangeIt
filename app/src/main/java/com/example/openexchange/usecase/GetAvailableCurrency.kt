package com.example.openexchange.usecase

import com.example.openexchange.ui.model.CurrencyUiModel

interface GetAvailableCurrency {
    suspend operator fun invoke(): CurrencyUiModel
}