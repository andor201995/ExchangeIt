package com.example.openexchange.repository.currency

import com.example.openexchange.repository.entity.toEntity
import com.example.openexchange.repository.entity.toModel
import com.example.openexchange.usecase.model.Currency
import javax.inject.Inject

class CurrencyDataSourceImpl @Inject constructor(private val dao: CurrencyDao) :
    CurrencyDataSource {
    override suspend fun addAll(values: List<Currency>) {
        dao.addAll(values.map { it.toEntity() })
    }

    override suspend fun removeAll() {
        dao.removeAll()
    }

    override suspend fun getAll(): List<Currency> {
        return dao.getAll().map { it.toModel() }
    }
}
