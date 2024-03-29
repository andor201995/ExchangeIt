package com.example.openexchange.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.openexchange.repository.currency.CurrencyDao
import com.example.openexchange.repository.entity.CurrencyEntity
import com.example.openexchange.repository.rate.RateDao
import com.example.openexchange.repository.entity.RateEntity

@Database(
    entities = [
        RateEntity::class,
        CurrencyEntity::class
    ], version = 1
)
abstract class DatabaseService : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "openexchange.db"
        private var instance: DatabaseService? = null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun getRateDao(): RateDao
    abstract fun getCurrencyDao(): CurrencyDao
}
