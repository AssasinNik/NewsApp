package com.nikitacherenkov.newsapp.domain.repository

import com.nikitacherenkov.newsapp.data.remote.dto.NewsDTO

interface NewsRepository {

    suspend fun getNews(country: String,category: String,apikey: String): NewsDTO

}