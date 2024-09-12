package com.nikitacherenkov.newsapp.presentation.main_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import com.nikitacherenkov.newsapp.utils.Constants.DEFAULT_IMAGE
import kotlinx.coroutines.Dispatchers

@Composable
fun TopNews(news: News , onTap: () -> Unit,) {
    Card(
        colors = CardDefaults.cardColors(Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(15.dp),
        modifier = Modifier
            .padding(10.dp)
            .height(300.dp)
            .clickable {
                onTap()
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f) // Задаем weight для Row
            ) {
                // Основная картинка новости
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (news.image_url!="" && news.image_url!=null){
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
                                .height(200.dp)
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
                    else{
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(DEFAULT_IMAGE)
                                .dispatcher(Dispatchers.IO)
                                .memoryCacheKey(DEFAULT_IMAGE)
                                .diskCacheKey(DEFAULT_IMAGE)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            contentDescription = DEFAULT_IMAGE,
                            contentScale = ContentScale.Crop,
                            filterQuality = FilterQuality.None,
                            modifier = Modifier
                                .height(200.dp)
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
                }
                if (news.source_icon!="" && news.source_icon!=null){
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(news.source_icon)
                            .dispatcher(Dispatchers.IO)
                            .memoryCacheKey(news.source_icon)
                            .diskCacheKey(news.source_icon)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = news.source_icon,
                        contentScale = ContentScale.Crop,
                        filterQuality = FilterQuality.None,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.Top)
                            .padding(10.dp),
                        loading = {
                            CircularProgressIndicator(
                                color = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
            if (news.title!="" && news.title!=null){
                Text(
                    text = news.title,
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 10.dp, end =10.dp, bottom = 10.dp, top = 7.dp)
                        .fillMaxWidth()
                )
            }
            else{
                Text(
                    text = "У данной новости нет названия",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(start = 10.dp, end =10.dp, bottom = 10.dp, top = 7.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}