package com.nikitacherenkov.newsapp.presentation.main_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.Dispatchers

@Composable
fun TopNews(
    news: News
){
    Card(
        colors = CardDefaults.cardColors(Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(15.dp),
        modifier = Modifier
            .width(350.dp)
            .padding(10.dp)
            .height(250.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
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
                        .width(170.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    loading = {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    }
                )
                if (news.title!=null){
                    Text(
                        text = news.title,
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Left
                    )
                }
            }
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
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black
                    )
                }
            )
        }
    }
}