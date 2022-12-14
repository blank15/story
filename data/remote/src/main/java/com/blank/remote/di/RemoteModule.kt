package com.blank.remote.di

import com.blank.remote.api.AuthService
import com.blank.remote.api.StoriesService
import com.blank.remote.datasource.AuthDataSource
import com.blank.remote.datasource.StoriesDataSource
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 60L

fun remoteModule(baseUrl: String) = module {
    factory<Interceptor> { AuthInterceptor(get()) }

    single {
        GsonBuilder()
            .registerTypeAdapterFactory(DataTypeAdapterFactory())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()
    }

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(get<Interceptor>())
            .retryOnConnectionFailure(true)
            .build()
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    factory { get<Retrofit>().create(AuthService::class.java) }
    factory { get<Retrofit>().create(StoriesService::class.java) }

    factory { AuthDataSource(get()) }
    factory { StoriesDataSource(get()) }
}