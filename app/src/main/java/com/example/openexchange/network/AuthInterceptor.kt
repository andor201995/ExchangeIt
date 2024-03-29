package com.example.openexchange.network

import com.example.openexchange.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url =
            req.url().newBuilder().addQueryParameter("app_id", Constants.APP_ID)
                .build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}
