package com.example.openexchange.usecase.di

import com.example.openexchange.usecase.DataOutdated
import com.example.openexchange.usecase.DataOutdatedImpl
import com.example.openexchange.usecase.GetAvailableCurrency
import com.example.openexchange.usecase.GetAvailableCurrencyImpl
import com.example.openexchange.usecase.GetExchangeRates
import com.example.openexchange.usecase.GetExchangeRatesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun bindGetExchangeRateUseCase(impl: GetExchangeRatesImpl): GetExchangeRates

    @Binds
    fun bindDataOutdated(impl: DataOutdatedImpl): DataOutdated

    @Binds
    fun bindGetAvailableCurrency(impl: GetAvailableCurrencyImpl): GetAvailableCurrency

}