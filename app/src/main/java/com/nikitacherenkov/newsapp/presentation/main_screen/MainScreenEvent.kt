package com.nikitacherenkov.newsapp.presentation.main_screen

import com.nikitacherenkov.newsapp.domain.model.News

sealed class MainScreenEvent {
    object OnUserAvatarClick: MainScreenEvent()
    data class onCategoryClick(val category: String): MainScreenEvent()
    data class OnNewsClick(val news: News): MainScreenEvent()
}