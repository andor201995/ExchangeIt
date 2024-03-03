package com.example.openexchange.repository.rate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openexchange.repository.entity.RateDbContract
import com.example.openexchange.repository.entity.RateEntity

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(rateEntityList: List<RateEntity>): List<Long>

    @Query("DELETE FROM ${RateDbContract.TABLE_NAME} WHERE ${RateDbContract.BASE_CURRENCY}=:baseCurrency")
    suspend fun remove(baseCurrency: String)

    @Query("DELETE FROM ${RateDbContract.TABLE_NAME}")
    suspend fun removeAll()

    @Query("Select DISTINCT ${RateDbContract.CURRENCY} FROM ${RateDbContract.TABLE_NAME}")
    suspend fun getCurrency(): List<String>

    @Query("Select * FROM ${RateDbContract.TABLE_NAME} WHERE ${RateDbContract.BASE_CURRENCY}=:baseCurrency")
    suspend fun get(baseCurrency: String): List<RateEntity>

    @Query("Select * FROM ${RateDbContract.TABLE_NAME}")
    suspend fun getAll(): List<RateEntity>
}