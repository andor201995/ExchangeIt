package com.example.openexchange.usecase

import com.example.openexchange.repository.OpenExchangeRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataOutdatedImplTest {
    @Mock
    private lateinit var repository: OpenExchangeRepository

    @InjectMocks
    private lateinit var systemUT: DataOutdatedImpl

    @Test
    fun `when diff less then 30 min return false`() = runTest {
        whenever(repository.getLastFetchTimeStamp()).thenReturn(1)
        whenever(repository.getCurrentTimeStamp()).thenReturn(180000)
        val result = systemUT()
        Assert.assertEquals(false, result)
    }

    @Test
    fun `when diff eq then 30 min return false`() = runTest {
        whenever(repository.getLastFetchTimeStamp()).thenReturn(1)
        whenever(repository.getCurrentTimeStamp()).thenReturn(1800001)
        val result = systemUT()
        Assert.assertEquals(false, result)
    }

    @Test
    fun `when diff greater then 30 min return false`() = runTest {
        whenever(repository.getLastFetchTimeStamp()).thenReturn(1)
        whenever(repository.getCurrentTimeStamp()).thenReturn(1800002)
        val result = systemUT()
        Assert.assertEquals(true, result)
    }

    @Test
    fun `when diff negative return true`() = runTest {
        whenever(repository.getLastFetchTimeStamp()).thenReturn(1800000)
        whenever(repository.getCurrentTimeStamp()).thenReturn(1)
        val result = systemUT()
        Assert.assertEquals(true, result)
    }
}