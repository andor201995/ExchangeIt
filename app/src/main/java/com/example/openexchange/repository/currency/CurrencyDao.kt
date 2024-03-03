package com.example.openexchange.repository.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openexchange.repository.entity.CurrencyDbContract
import com.example.openexchange.repository.entity.CurrencyEntity

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(currencyEntityList: List<CurrencyEntity>): List<Long>

    @Query("DELETE FROM ${CurrencyDbContract.TABLE_NAME}")
    suspend fun removeAll()

    @Query("Select * FROM ${CurrencyDbContract.TABLE_NAME} GROUP BY ${CurrencyDbContract.CODE}")
    suspend fun getAll(): List<CurrencyEntity>
}
