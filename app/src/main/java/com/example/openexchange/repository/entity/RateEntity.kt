package com.example.openexchange.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.openexchange.usecase.model.Rate

@Entity(tableName = RateDbContract.TABLE_NAME)
data class RateEntity(
    @ColumnInfo(name = RateDbContract.CURRENCY)
    @PrimaryKey
    val currencyCode: String,
    @ColumnInfo(name = RateDbContract.VALUE)
    val value: Double,
    @ColumnInfo(name = RateDbContract.BASE_CURRENCY)
    val baseCurrencyCode: String
)

object RateDbContract {
    const val TABLE_NAME = "rates"
    const val CURRENCY = "currency"
    const val BASE_CURRENCY = "base_currency"
    const val VALUE = "value"
}

internal fun Rate.toEntity(): RateEntity = RateEntity(currencyCode, value, baseCurrencyCode)
internal fun RateEntity.toModel(): Rate = Rate(currencyCode, value, baseCurrencyCode)
