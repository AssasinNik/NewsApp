package com.nikitacherenkov.newsapp.presentation.news_screen

import android.content.Context

sealed class NewsScreenEvent {
    object OnBackIconClick: NewsScreenEvent()
    data class OnShareIconClick(val newsLink: String, val context: Context): NewsScreenEvent()
}