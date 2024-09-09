package com.nikitacherenkov.newsapp.domain.use_case.get_news

import coil.network.HttpException
import com.nikitacherenkov.newsapp.data.remote.dto.toNews
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.domain.repository.NewsRepository
import com.nikitacherenkov.newsapp.utils.Constants.API_KEY
import com.nikitacherenkov.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: String): Flow<Resource<List<News>>> = flow {
        try {
            emit(Resource.Loading())
            val news = repository.getNews("ru", category, API_KEY)
            emit(Resource.Success(news.results.map { it.toNews() }))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}