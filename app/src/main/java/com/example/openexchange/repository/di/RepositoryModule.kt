package com.example.openexchange.repository.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.openexchange.common.Constants.SHARED_PREF_TIMESTAMP
import com.example.openexchange.repository.OpenExchangeRepository
import com.example.openexchange.repository.OpenExchangeRepositoryImpl
import com.example.openexchange.repository.currency.CurrencyDataSource
import com.example.openexchange.repository.currency.CurrencyDataSourceImpl
import com.example.openexchange.repository.database.DatabaseService
import com.example.openexchange.repository.rate.RateDataSource
import com.example.openexchange.repository.rate.RateDataSourceImpl
import com.example.openexchange.repository.timestamp.TimeStampDataSource
import com.example.openexchange.repository.timestamp.TimeStampDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideDatabaseService(@ApplicationContext appContext: Context) =
        DatabaseService.getInstance(appContext)

    @Provides
    fun provideRateDao(databaseService: DatabaseService) = databaseService.getRateDao()

    @Provides
    fun provideCurrencyDao(databaseService: DatabaseService) = databaseService.getCurrencyDao()

    @Provides
    @Named(SHARED_PREF_TIMESTAMP)
    fun provideTimeStampSharePref(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(SHARED_PREF_TIMESTAMP, MODE_PRIVATE)

    @InstallIn(SingletonComponent::class)
    @Module
    interface Bindings {
        @Binds
        fun bindRepository(impl: OpenExchangeRepositoryImpl): OpenExchangeRepository

        @Binds
        fun bindRateDateSource(impl: RateDataSourceImpl): RateDataSource

        @Binds
        fun bindCurrencyDataSource(impl: CurrencyDataSourceImpl): CurrencyDataSource

        @Binds
        fun bindTimeStampDateSource(impl: TimeStampDataSourceImpl): TimeStampDataSource
    }
}