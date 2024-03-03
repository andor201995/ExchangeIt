package com.example.openexchange.helper

import com.example.openexchange.network.OpenExchangeApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import java.net.HttpURLConnection

object MockServerRetrofit {
    private val json = Json { ignoreUnknownKeys = true }

    fun getOpenExchangeApi(mMockWebServer: MockWebServer): OpenExchangeApi {
        val okHttpClient: OkHttpClient = getOkHttpWithCustomDispatcher()
        return Retrofit.Builder()
            .baseUrl(mMockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(OpenExchangeApi::class.java)
    }

    private fun getOkHttpWithCustomDispatcher(): OkHttpClient {
        val currentThreadExecutor = CurrentThreadExecutor()
        val dispatcher = Dispatcher(currentThreadExecutor)
        return OkHttpClient().newBuilder().dispatcher(dispatcher).build()
    }

    fun webServerSuccess(mockWebServer: MockWebServer, data: String) {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(data)
        mockWebServer.enqueue(response)
    }

    fun webServerFailure(mockWebServer: MockWebServer) {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        mockWebServer.enqueue(response)
    }
}
