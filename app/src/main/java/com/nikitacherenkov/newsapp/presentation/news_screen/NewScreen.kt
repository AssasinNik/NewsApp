package com.nikitacherenkov.newsapp.presentation.news_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    BoxWithConstraints {
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        val rectHeight = screenHeight / 2
        val cornerRadius = 20.dp
        val imageSize = 120.dp
        val imageOffset = 20.dp
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(news.image_url)
                .dispatcher(Dispatchers.IO)
                .memoryCacheKey(news.title)
                .diskCacheKey(news.title)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "News Image",
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .height(maxHeight / 3)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            ,
            loading = {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        )
        val contentHeight = screenHeight + 250.dp + imageSize + imageOffset
        Box(
            modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true)
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(contentHeight)
            ) {
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(0f, screenHeight.toPx() / 2 - rectHeight.toPx() / 2),
                    size = Size(size.width, contentHeight.toPx()*2),
                    cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ){
                if (screenWidth <= 480.dp) {
                    Spacer(modifier = Modifier.height(screenHeight / 2 - imageSize / 2 - imageOffset - 80.dp))
                }
                else{
                    Spacer(modifier = Modifier.height(screenHeight / 2 - imageSize / 2 - imageOffset - 200.dp))
                }
                Text(
                    text = news.title.toString(),
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(10.dp))
                if(news.description!=null || news.description!=""){
                    Text(
                        text = news.description.toString(),
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                else{
                    Text(
                        text = "У данно новости нету описания. Сожалеем, что так получилось",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}