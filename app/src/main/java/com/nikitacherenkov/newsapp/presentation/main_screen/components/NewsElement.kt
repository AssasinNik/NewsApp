package com.nikitacherenkov.newsapp.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun NewsElement(
    news: News
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp)
            .clickable {

            }
    ){
        if (news.source_icon!="" || news.source_icon!=null){
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
                    .height(60.dp)
                    .width(60.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp)) // Применение закругленных углов
                    .align(Alignment.CenterVertically),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black
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
                contentDescription = news.title,
                contentScale = ContentScale.Fit,
                filterQuality = FilterQuality.None,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterVertically),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Black
                    )
                }
            )
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = news.title.toString(),
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}