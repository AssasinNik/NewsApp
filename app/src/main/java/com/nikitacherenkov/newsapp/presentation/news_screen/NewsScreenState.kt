package com.nikitacherenkov.newsapp.presentation.news_screen

import com.nikitacherenkov.newsapp.domain.model.News

data class NewsScreenState(
    val isLoading: Boolean = false,
    val news: News? = null,
    val error: String = ""
)