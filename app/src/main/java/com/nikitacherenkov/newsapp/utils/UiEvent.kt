package com.nikitacherenkov.newsapp.utils

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object PopBackStack: UiEvent()
}