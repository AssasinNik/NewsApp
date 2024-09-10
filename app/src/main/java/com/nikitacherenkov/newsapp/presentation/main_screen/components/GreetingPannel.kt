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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikitacherenkov.newsapp.R
import coil.request.CachePolicy
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreenEvent
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreenState
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreenViewModel
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun GreetingPanel(
    state: MainScreenState,
    viewModel: MainScreenViewModel
){
    Row (
        modifier = Modifier
            .padding(start=18.dp, end=18.dp , top=70.dp, bottom = 40.dp)
            .fillMaxWidth()
    ){
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.person_image)
                .dispatcher(Dispatchers.IO)
                .memoryCacheKey("person_image")
                .diskCacheKey("person_image")
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "Person Image",
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .height(100.dp)
                .width(90.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.CenterVertically)
                .clickable {
                    viewModel.onEvent(MainScreenEvent.OnUserAvatarClick)
                }
            ,
            loading = {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Text(
                text = "Hello\uD83D\uDC4B",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Left
            )
            Text(
                text = state.date,
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
}