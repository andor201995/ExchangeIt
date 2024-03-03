package com.example.openexchange.repository.rate

import com.example.openexchange.repository.entity.toEntity
import com.example.openexchange.repository.entity.toModel
import com.example.openexchange.usecase.model.Rate
import javax.inject.Inject

class RateDataSourceImpl @Inject constructor(private val dao: RateDao) : RateDataSource {
    override suspend fun addAll(values: List<Rate>) {
        dao.addAll(values.map { it.toEntity() })
    }

    override suspend fun removeAll() {
        dao.removeAll()
    }

    override suspend fun get(baseCurrency: String): List<Rate> {
        return dao.get(baseCurrency).map { it.toModel() }
    }

    override suspend fun getAllCurrencyCode(): List<String> {
        return dao.getCurrency()
    }
}