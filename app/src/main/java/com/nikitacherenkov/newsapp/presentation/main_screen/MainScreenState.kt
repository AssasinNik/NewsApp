package com.nikitacherenkov.newsapp.presentation.main_screen

import com.nikitacherenkov.newsapp.domain.model.News

data class MainScreenState(
    val isLoading: Boolean = false,
    val news : List<News> = emptyList(),
    val allNews: List<News> = emptyList(),
    val categories: List<String> = listOf(
        "top", "sports", "technology",
        "business", "science", "entertainment",
        "health", "world", "politics",
        "environment"
    ),
    var chosenCategories: List<String> = emptyList(),
    val error : String = "",
    val date: String =""
)
