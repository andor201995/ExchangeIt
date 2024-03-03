package com.example.openexchange.repository.rate

import com.example.openexchange.repository.entity.RateEntity
import com.example.openexchange.usecase.model.Rate
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RateDataSourceImplTest {

    @Mock
    private lateinit var dao: RateDao

    @InjectMocks
    private lateinit var systemUT: RateDataSourceImpl


    @Test
    fun `test addAll`() = runTest {
        val input = listOf(Rate("THB", 0.0, "USD"))
        val expectedOutput = listOf(RateEntity("THB", 0.0, "USD"))
        systemUT.addAll(input)
        verify(dao).addAll(expectedOutput)
    }

    @Test
    fun `test removeAll`() = runTest {
        systemUT.removeAll()
        verify(dao).removeAll()
    }

    @Test
    fun `test get`() = runTest {
        whenever(dao.get(any())).thenReturn(emptyList())
        systemUT.get("USD")
        verify(dao).get("USD")
    }

    @Test
    fun `test getAllCurrencyCode`() = runTest {
        systemUT.getAllCurrencyCode()
        verify(dao).getCurrency()
    }
}