package com.mosz.sportapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mosz.sportapp.BuildConfig
import com.mosz.sportapp.data.remote.MainSportService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val INTERCEPTOR_LOGGING = "INTERCEPTOR_LOGGING"

    @Provides
    @Named(INTERCEPTOR_LOGGING)
    fun provideHttpLoggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            noOpInterceptor()
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(INTERCEPTOR_LOGGING) loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            serializersModule = SerializersModule { classDiscriminator = "type" }
        }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder().addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.API_BASE_URL).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): MainSportService =
        retrofit.create(MainSportService::class.java)

    private fun noOpInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request())
        }
    }
}
