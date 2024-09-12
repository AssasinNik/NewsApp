package com.nikitacherenkov.newsapp.presentation.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.nikitacherenkov.newsapp.presentation.main_screen.components.GreetingPanel
import com.nikitacherenkov.newsapp.presentation.main_screen.components.NewsElement
import com.nikitacherenkov.newsapp.presentation.main_screen.components.ShimmerNews
import com.nikitacherenkov.newsapp.presentation.main_screen.components.TopNews
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerCard
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerSource
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerText
import com.nikitacherenkov.newsapp.presentation.ui.theme.BorderColor
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import com.nikitacherenkov.newsapp.utils.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getNews()
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    val state = viewModel.state.value
    val news = state.news
    val allNews = state.allNews
    val pagerState = rememberPagerState(initialPage = 2) {
        state.news.size
    }

    val listState = rememberLazyListState()
    val showCategories by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 5
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                GreetingPanel(state, viewModel)
            }
            item {
                Text(
                    text = "Популярно",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            item {
                if (news.isNotEmpty() && !state.isLoading) {
                    HorizontalPager(
                        contentPadding = PaddingValues(horizontal = 26.dp),
                        state = pagerState,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) { page ->
                        TopNews(news = news[page]) {
                            viewModel.onEvent(MainScreenEvent.OnNewsClick(news[page]))
                        }
                    }
                } else {
                    ShimmerCard()
                }
            }

            item {
                Text(
                    text = "Новости",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            item {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    state.categories.forEach { element ->
                        if (element != "top"){
                            val isFavorite = state.chosenCategories.contains(element)
                            val backgroundColor = if (isFavorite) Color.Black else Color.White
                            val textColor = if (isFavorite) Color.White else Color.Black

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = backgroundColor,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clickable {
                                        viewModel.onEvent(MainScreenEvent.onCategoryClick(element))
                                    }
                            ) {
                                Text(
                                    text = element,
                                    fontSize = 16.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }

            if (allNews.isNotEmpty() && !state.isLoading) {
                val filteredNews = allNews.filter { news ->
                    news.categories.any { category ->
                        state.chosenCategories.contains(category)
                    }
                }
                if (filteredNews.isEmpty()){
                    items(allNews) { element ->
                        NewsElement(news = element){
                            viewModel.onEvent(MainScreenEvent.OnNewsClick(element))
                        }
                    }
                }
                else{
                    items(filteredNews) { element ->
                        NewsElement(news = element){
                            viewModel.onEvent(MainScreenEvent.OnNewsClick(element))
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            } else {
                items(2) {
                    ShimmerNews()
                }
            }
        }

        AnimatedVisibility(
            visible = showCategories,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.White)
                .shadow(2.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "News",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 40.dp, start = 20.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    state.categories.forEach { element ->
                        if (element != "top"){
                            val isFavorite = state.chosenCategories.contains(element)
                            val backgroundColor = if (isFavorite) Color.Black else Color.White
                            val textColor = if (isFavorite) Color.White else Color.Black

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = backgroundColor,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clickable {
                                        viewModel.onEvent(MainScreenEvent.onCategoryClick(element))
                                    }
                            ) {
                                Text(
                                    text = element,
                                    fontSize = 16.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
