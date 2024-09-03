package com.nikitacherenkov.newsapp.data.remote.dto

data class NewsDTO(
    val nextPage: String,
    val results: List<ResultDTO>,
    val status: String,
    val totalResults: Int
)