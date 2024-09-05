package com.nikitacherenkov.newsapp.presentation.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nikitacherenkov.newsapp.presentation.main_screen.components.GreetingPannel
import com.nikitacherenkov.newsapp.presentation.main_screen.components.TopNews

@Composable
fun MainScreen(
    //navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getNews()
    }
    val state = viewModel.state.value
    val news = state.news
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GreetingPannel(state)
        if (!news.isEmpty()) {
            TopNews(news = news[0])
        }
    }
}