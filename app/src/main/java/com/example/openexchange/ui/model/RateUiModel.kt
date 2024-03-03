package com.example.openexchange.ui.model

import com.example.openexchange.usecase.model.Rate

data class RateUiModel(
    val rates: List<Rate> = emptyList(),
)

