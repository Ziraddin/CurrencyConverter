package com.example.currencyconverter.presentation.core.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencyconverter.data.interceptor.ApiKeyInterceptor
import com.example.currencyconverter.data.remote.CurrencyApi
import com.example.currencyconverter.data.repository.ApiKeyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.freecurrencyapi.com/v1/"

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideApiKeyRepository(@ApplicationContext context: Context): ApiKeyRepository {
        return ApiKeyRepository(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideApiKeyInterceptor(apiKeyRepository: ApiKeyRepository): ApiKeyInterceptor {
        return ApiKeyInterceptor {
            apiKeyRepository.apiKey.value
        }
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
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}
