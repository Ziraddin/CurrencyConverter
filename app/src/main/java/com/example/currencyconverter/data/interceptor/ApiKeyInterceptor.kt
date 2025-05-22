package com.example.currencyconverter.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKeyProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var apiKey = apiKeyProvider() ?: ""
        var newRequest = originalRequest.newBuilder().addHeader("apikey", apiKey).build()
        var response = chain.proceed(newRequest)
        if (!response.isSuccessful) {
            response.close()
            repeat(3) {
                Thread.sleep(1000)
                apiKey = apiKeyProvider() ?: ""
                newRequest = originalRequest.newBuilder().addHeader("apikey", apiKey).build()
                response = chain.proceed(newRequest)
                if (response.isSuccessful) {
                    return response
                }
                response.close()
            }
        }

        return response
    }
}
