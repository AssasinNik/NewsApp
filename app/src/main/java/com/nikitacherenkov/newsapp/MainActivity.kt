package com.nikitacherenkov.newsapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nikitacherenkov.newsapp.presentation.main_screen.components.GreetingPannel
import com.nikitacherenkov.newsapp.presentation.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface(modifier = Modifier.background(Color.White), color = Color.White) {
                    GreetingPannel()
                }
            }
        }
    }
}
