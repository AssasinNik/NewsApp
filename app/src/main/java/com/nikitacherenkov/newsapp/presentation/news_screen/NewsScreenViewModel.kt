package com.nikitacherenkov.newsapp.presentation.news_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.domain.use_case.get_news.GetNewsUseCase
import com.nikitacherenkov.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val useCase: GetNewsUseCase
): ViewModel(){
    private val _state = mutableStateOf(NewsScreenState())
    val state : State<NewsScreenState> = _state

    var news: News? = null

    private fun getNews(title: String){
        useCase("").onEach { result ->
            when(result){
                is Resource.Success -> {
                    if (result.data != null){
                        for (i in result.data){
                            if(i.title == title){
                                news = i
                            }
                        }
                    }
                    _state.value = NewsScreenState(
                        news = news
                    )
                }
                is Resource.Error -> {
                    _state.value = NewsScreenState(
                        error = result.message ?: "Unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = NewsScreenState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}