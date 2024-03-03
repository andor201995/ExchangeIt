package com.example.openexchange.network.di

import com.example.openexchange.common.Constants
import com.example.openexchange.network.AuthInterceptor
import com.example.openexchange.network.OpenExchangeApi
import com.example.openexchange.network.OpenExchangeEndpoint
import com.example.openexchange.network.OpenExchangeEndpointImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideRetroFit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    fun provideOpenExchangeApi(retrofit: Retrofit): OpenExchangeApi =
        retrofit.create(OpenExchangeApi::class.java)

    @Provides
    @Named("IO")
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @InstallIn(SingletonComponent::class)
    @Module
    interface Bindings {
        @Binds
        fun bindOpenExchangeEndpoint(impl: OpenExchangeEndpointImpl): OpenExchangeEndpoint
    }
}