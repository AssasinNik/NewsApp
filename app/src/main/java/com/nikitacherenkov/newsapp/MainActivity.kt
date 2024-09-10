package com.nikitacherenkov.newsapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.presentation.main_screen.MainScreen
import com.nikitacherenkov.newsapp.presentation.news_screen.NewsScreen
import com.nikitacherenkov.newsapp.presentation.ui.theme.NewsAppTheme
import com.nikitacherenkov.newsapp.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface(modifier = Modifier.background(Color.White), color = Color.White) {
                    val navController = rememberNavController()
                    val gson = Gson()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MAIN_SCREEN,
                        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))},
                        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))},
                        popEnterTransition =  { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))},
                        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))}
                    ){
                        composable(
                            route = Routes.MAIN_SCREEN
                        ){
                            MainScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = "${Routes.NEWS_SCREEN}/{newsJson}"
                        ){ backStackEntry ->
                            val newsJson = Uri.decode(backStackEntry.arguments?.getString("newsJson"))  // Декодируем JSON
                            val news = gson.fromJson(newsJson, News::class.java)
                            NewsScreen(
                                news = news,
                                onNavigate = { navController.navigate(it.route) },
                                onPopBackStack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
