package com.nikitacherenkov.newsapp.presentation.news_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikitacherenkov.newsapp.R
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreenEvent
import com.nikitacherenkov.newsapp.presentation.ui.theme.BorderColor
import com.nikitacherenkov.newsapp.presentation.ui.theme.InfoColor
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import com.nikitacherenkov.newsapp.utils.UiEvent
import kotlinx.coroutines.Dispatchers


@Composable
fun NewsScreen(
    news: News,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: (UiEvent.PopBackStack) -> Unit,
    viewModel: NewsScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack(event)
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.source_icon)
                    .dispatcher(Dispatchers.IO)
                    .memoryCacheKey(news.source_icon)
                    .diskCacheKey(news.source_icon)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.None,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black
                    )
                }
            )
            Text(
                text = news.source_name.toString()+" · "+ news.pubDate + " · " + news.categories[0],
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    color = InfoColor,
                    fontSize = 17.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Text(
            text = news.title.toString(),
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 25.sp
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        if(news.image_url!=null && news.image_url!="" && news.image_url!="null"){
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.image_url)
                    .dispatcher(Dispatchers.IO)
                    .memoryCacheKey(news.image_url)
                    .diskCacheKey(news.image_url)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = news.image_url,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.None,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (news.description!=null && news.description!="" && news.description!="null"){
            Text(
                text = news.description.toString(),
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
        else{
            Text(
                text = "К сожалению, у данной новости нет описания. Попробуйте просмотреть данную новость в браузере",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f)
            .background(Color.Transparent)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(130.dp)
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 10.dp, bottom = 8.dp)
                    .size(45.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape) // Apply shadow here
                    .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(NewsScreenEvent.OnBackIconClick)
                    },
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back-To-Previous",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            val context = LocalContext.current
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = 30.dp, end = 10.dp, bottom = 8.dp)
                    .size(45.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(NewsScreenEvent.OnShareIconClick(
                            newsLink = news.link.toString(),
                            context = context
                        ))
                    },
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share news",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}