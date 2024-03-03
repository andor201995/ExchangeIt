package com.example.openexchange.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.openexchange.usecase.model.Currency

@Entity(tableName = CurrencyDbContract.TABLE_NAME)
data class CurrencyEntity(
    @ColumnInfo(name = CurrencyDbContract.CODE)
    @PrimaryKey
    val currencyCode: String,
    @ColumnInfo(name = CurrencyDbContract.NAME)
    val name: String
)

object CurrencyDbContract {
    const val TABLE_NAME = "currency"
    const val CODE = "code"
    const val NAME = "name"
}

fun CurrencyEntity.toModel(): Currency {
    return Currency(this.currencyCode, this.name)
}

fun Currency.toEntity(): CurrencyEntity {
    return CurrencyEntity(this.code, this.name)
}