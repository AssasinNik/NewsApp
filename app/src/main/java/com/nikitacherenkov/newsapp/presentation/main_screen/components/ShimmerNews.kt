package com.nikitacherenkov.newsapp.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerSource
import com.nikitacherenkov.newsapp.presentation.reusable_components.ShimmerText
import com.nikitacherenkov.newsapp.presentation.ui.theme.BorderColor

@Composable
fun ShimmerNews(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp)
    ){
        ShimmerSource()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp))
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderColor)
            )
            Spacer(modifier = Modifier.height(10.dp))
            ShimmerText()
        }
    }
}