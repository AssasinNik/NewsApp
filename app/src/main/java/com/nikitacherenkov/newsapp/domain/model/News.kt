package com.nikitacherenkov.newsapp.domain.model

data class News(
    val category: List<String>,
    val description: String,
    val image_url: String,
    val keywords: List<String>,
    val link: String,
    val pubDate: String,
    val source_icon: String,
    val source_name: String,
    val source_url: String,
    val title: String,
)
