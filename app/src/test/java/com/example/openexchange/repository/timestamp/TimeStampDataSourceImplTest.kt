package com.example.openexchange.repository.timestamp

import android.content.SharedPreferences
import com.example.openexchange.repository.timestamp.TimeStampDataSourceImpl.Companion.KEY_TIMESTAMP
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TimeStampDataSourceImplTest {

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    @InjectMocks
    lateinit var systemUT: TimeStampDataSourceImpl

    @Test
    fun `test getLastUpdate`() = runTest {
        systemUT.getLastUpdate()
        verify(sharedPreferences).getLong(KEY_TIMESTAMP, 0L)
    }

    @Test
    fun `test setLastUpdate`() = runTest {
        whenever(sharedPreferences.edit()).thenReturn(editor)
        systemUT.setLastUpdate(0L)
        verify(editor).putLong(KEY_TIMESTAMP, 0L)
        verify(editor).apply()
        verify(sharedPreferences).edit()
    }

}