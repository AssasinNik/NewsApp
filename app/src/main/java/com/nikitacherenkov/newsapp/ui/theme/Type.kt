package com.nikitacherenkov.newsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nikitacherenkov.newsapp.R

val Poppins = FontFamily(
    Font(R.font.poppinsbold, FontWeight.Bold),
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppinslight, FontWeight.Light)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)