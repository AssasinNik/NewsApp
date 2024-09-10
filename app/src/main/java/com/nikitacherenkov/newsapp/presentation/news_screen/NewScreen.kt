package com.nikitacherenkov.newsapp.presentation.news_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.utils.UiEvent


@Composable
fun NewsScreen(
    news: News,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: (UiEvent.PopBackStack) -> Unit,
    viewModel: NewsScreenViewModel = hiltViewModel()
) {

}