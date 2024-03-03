package com.example.openexchange.network

import com.example.openexchange.helper.MockServerRetrofit
import com.example.openexchange.helper.TestData
import com.example.openexchange.usecase.model.Currency
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OpenExchangeEndpointImplTest {
    private var mMockWebServer = MockWebServer()
    private lateinit var systemUT: OpenExchangeEndpoint

    @Before
    fun setup() {
        mMockWebServer.start()
        val api = MockServerRetrofit.getOpenExchangeApi(mMockWebServer)

        systemUT = OpenExchangeEndpointImpl(api)
    }

    @After
    fun teardown() {
        mMockWebServer.shutdown()
    }

    @Test
    fun `test endpoint to fetch rates`() = runTest {
        MockServerRetrofit.webServerSuccess(
            mMockWebServer,
            TestData.RATES_SERVER_RESPONSE_JSON_SUCCESS
        )
        val result = systemUT.fetchExchangeRates("USD")

        Assert.assertEquals(TestData.SERVER_RESPONSE_EXCHANGE_SCHEMA.toModel(), result)
    }

    @Test
    fun `test endpoint to fetch currency`() = runTest {
        MockServerRetrofit.webServerSuccess(
            mMockWebServer,
            TestData.CURRENCY_SERVER_RESPONSE_JSON_SUCCESS
        )
        val result = systemUT.fetchCurrency()

        Assert.assertEquals(
            listOf(
                Currency("AED", "United Arab Emirates Dirham"),
                Currency("AFN", "Afghan Afghani")
            ),
            result
        )
    }
}