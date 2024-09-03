package com.nikitacherenkov.newsapp.data.remote.dto

import com.nikitacherenkov.newsapp.domain.model.News

data class ResultDTO(
    val ai_org: String,
    val ai_region: String,
    val ai_tag: String,
    val article_id: String,
    val category: List<String>,
    val content: String,
    val country: List<String>,
    val creator: List<String>,
    val description: String,
    val duplicate: Boolean,
    val image_url: String,
    val keywords: List<String>,
    val language: String,
    val link: String,
    val pubDate: String,
    val pubDateTZ: String,
    val sentiment: String,
    val sentiment_stats: String,
    val source_icon: String,
    val source_id: String,
    val source_name: String,
    val source_priority: Int,
    val source_url: String,
    val title: String,
    val video_url: Any
)

fun ResultDTO.toNews(): News{
    return News(
        category = category,
        description = description,
        image_url = image_url,
        keywords = keywords,
        link = link,
        pubDate = pubDate,
        source_icon = source_icon,
        source_name = source_name,
        source_url = source_url,
        title = title,
    )
}