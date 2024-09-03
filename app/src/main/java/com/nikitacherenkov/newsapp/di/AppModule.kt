package com.nikitacherenkov.newsapp.di

import com.nikitacherenkov.newsapp.data.remote.RussianNewsApi
import com.nikitacherenkov.newsapp.data.repository.NewsRepositoryImpl
import com.nikitacherenkov.newsapp.domain.repository.NewsRepository
import com.nikitacherenkov.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): RussianNewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RussianNewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: RussianNewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }
}