package com.nikitacherenkov.newsapp.di

import com.nikitacherenkov.newsapp.data.remote.RussianNewsApi
import com.nikitacherenkov.newsapp.data.repository.NewsRepositoryImpl
import com.nikitacherenkov.newsapp.domain.repository.NewsRepository
import com.nikitacherenkov.newsapp.domain.use_case.get_news.GetNewsUseCase
import com.nikitacherenkov.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): RussianNewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RussianNewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: RussianNewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add the logging interceptor
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }
}