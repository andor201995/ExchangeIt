package com.example.openexchange.repository.currency

import com.example.openexchange.usecase.model.Currency

interface CurrencyDataSource {
    suspend fun addAll(values: List<Currency>)
    suspend fun removeAll()
    suspend fun getAll(): List<Currency>
}
