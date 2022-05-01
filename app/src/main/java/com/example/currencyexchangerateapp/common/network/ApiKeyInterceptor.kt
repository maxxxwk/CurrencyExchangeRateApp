package com.example.currencyexchangerateapp.common.network

import com.example.currencyexchangerateapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(
            request.newBuilder()
                .url(
                    request.url
                        .newBuilder()
                        .addQueryParameter(API_KEY_PARAMETER_NAME, BuildConfig.API_KEY)
                        .build()
                ).build()
        )
    }

    private companion object {
        const val API_KEY_PARAMETER_NAME = "api_key"
    }
}
