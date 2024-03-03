package com.example.openexchange.repository.currency

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyDataSourceImplTest {
    @Mock
    private lateinit var dao: CurrencyDao

    @InjectMocks
    private lateinit var systemUT: CurrencyDataSourceImpl

    @Test
    fun `test addAll`() = runTest {
        systemUT.addAll(emptyList())
        verify(dao).addAll(emptyList())
    }

    @Test
    fun `test removeAll`() = runTest {
        systemUT.removeAll()
        verify(dao).removeAll()
    }

    @Test
    fun `test getAll`() = runTest {
        whenever(dao.getAll()).thenReturn(emptyList())
        systemUT.getAll()
        verify(dao).getAll()
    }
}