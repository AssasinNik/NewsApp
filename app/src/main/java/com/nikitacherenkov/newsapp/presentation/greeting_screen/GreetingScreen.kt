package com.nikitacherenkov.newsapp.presentation.greeting_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nikitacherenkov.newsapp.R
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreenEvent
import com.nikitacherenkov.newsapp.presentation.ui.theme.Poppins
import kotlinx.coroutines.Dispatchers

@Composable
fun GreetingScreen(){
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.wallpaper)
            .dispatcher(Dispatchers.IO)
            .memoryCacheKey("wallpapper")
            .diskCacheKey("wallpaper")
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = "Person Image",
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.None,
        modifier = Modifier
            .fillMaxSize()
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ),
                startY = 500f
            )
        )
    )

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Привет!",
            style = TextStyle(
                fontFamily = Poppins,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Оставайся в курсе событий) И следи за новостями в нашем приложении",
            style = TextStyle(
                fontFamily = Poppins,
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))
        Button(
            onClick = {
                // Your button's action goes here
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(65.dp)
                .fillMaxWidth()
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007BFF), // Example blue color
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Приступим",
                style = TextStyle(
                    fontFamily = Poppins, // Assuming you have Poppins font
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}