package com.example.openexchange.usecase

import com.example.openexchange.repository.OpenExchangeRepository
import javax.inject.Inject

class DataOutdatedImpl @Inject constructor(
    private val repository: OpenExchangeRepository
) : DataOutdated {
    override suspend fun invoke(): Boolean {
        val diff = lastFetchDiffInMilliSec()
        return if (diff > 0) (repository.getLastFetchTimeStamp() == 0L ||
                diff > MAX_DB_TIMESTAMP_IN_MIN)
        else true
    }

    private suspend fun lastFetchDiffInMilliSec() =
        repository.getCurrentTimeStamp() - repository.getLastFetchTimeStamp()

    companion object {
        private const val MAX_DB_TIMESTAMP_IN_MIN = 30 * 60000
    }
}