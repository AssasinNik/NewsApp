package com.nikitacherenkov.newsapp.presentation.main_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nikitacherenkov.newsapp.presentation.main_screen.components.GreetingPannel
import com.nikitacherenkov.newsapp.presentation.main_screen.components.TopNews
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins

@OptIn(ExperimentalFoundationApi::class)
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
    val pagerState = rememberPagerState(initialPage = 2){
        state.news.size
    }
    val horizontalScrollState = rememberScrollState()
    val categories: List<String> = listOf(
        "top", "sports", "technology",
        "business", "science", "entertainment",
        "health", "world", "politics",
        "environment","food"
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GreetingPannel(state)
        if (!news.isEmpty()) {
            Text(
                text = "Hot News",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 10.dp)
            )
            HorizontalPager(
                contentPadding = PaddingValues(horizontal = 26.dp),
                state = pagerState,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                ) { page ->
                TopNews(news = news[page])
            }
            Text(
                text = "Recent news",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .horizontalScroll(horizontalScrollState)
            ) {
                categories.map { element->
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(
                                horizontal = 12.dp
                            )
                    ) {
                        Text(
                            text = element,
                            fontSize = 16.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}