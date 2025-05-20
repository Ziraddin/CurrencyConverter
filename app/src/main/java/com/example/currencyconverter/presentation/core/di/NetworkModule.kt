package com.example.currencyconverter.presentation.core.di

import com.example.currencyconverter.data.interceptor.ApiKeyInterceptor
import com.example.currencyconverter.data.remote.ICurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.freecurrencyapi.com/v1/"

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor("fca_live_rlHFx1SRm8mkY0kqvwmZuwBk68oF1ASQbxP41RZ8")
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): ICurrencyApi {
        return retrofit.create(ICurrencyApi::class.java)
    }
}
