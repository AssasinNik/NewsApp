package com.nikitacherenkov.newsapp.presentation.main_screen

import com.nikitacherenkov.newsapp.domain.model.News

data class MainScreenState(
    val isLoading: Boolean = false,
    val news : List<News> = emptyList(),
    val error : String = "",
    val date: String =""
)
