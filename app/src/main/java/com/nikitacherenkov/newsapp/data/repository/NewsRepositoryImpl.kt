package com.nikitacherenkov.newsapp.data.repository

import com.nikitacherenkov.newsapp.data.remote.RussianNewsApi
import com.nikitacherenkov.newsapp.data.remote.dto.NewsDTO
import com.nikitacherenkov.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: RussianNewsApi
) : NewsRepository {
    override suspend fun getNews(country: String, category: String, apikey: String): List<NewsDTO> {
        return api.getNews(country, category, apikey)
    }
}