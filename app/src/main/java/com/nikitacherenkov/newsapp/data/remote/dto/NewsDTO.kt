package com.nikitacherenkov.newsapp.data.remote.dto

data class NewsDTO(
    val status: String,
    val totalResults: Int,
    val results: List<ResultDTO>,
    val nextPage: String
)