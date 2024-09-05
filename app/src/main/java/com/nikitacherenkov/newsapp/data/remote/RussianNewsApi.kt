package com.nikitacherenkov.newsapp.data.remote

import com.nikitacherenkov.newsapp.data.remote.dto.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RussianNewsApi {

    @GET("/api/1/latest")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apikey") apikey: String
    ): NewsDTO
}