package com.nikitacherenkov.newsapp.presentation.main_screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nikitacherenkov.newsapp.domain.model.News
import com.nikitacherenkov.newsapp.domain.use_case.get_news.GetNewsUseCase
import com.nikitacherenkov.newsapp.utils.Resource
import com.nikitacherenkov.newsapp.utils.Routes
import com.nikitacherenkov.newsapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCase: GetNewsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun getActualDate(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())
        val formattedDate = today.format(formatter)
        return formattedDate
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.OnUserAvatarClick -> {
                sendUiEvent(UiEvent.Navigate(""))
            }
            is MainScreenEvent.OnNewsClick -> {
                val gson = Gson()
                val newsJson = Uri.encode(gson.toJson(event.news))
                sendUiEvent(UiEvent.Navigate("${Routes.NEWS_SCREEN}/$newsJson"))
            }
            is MainScreenEvent.onCategoryClick -> {
                val currentCategories = _state.value.chosenCategories.toMutableList()

                if (currentCategories.contains(event.category)) {
                    currentCategories.remove(event.category)
                } else {
                    currentCategories.add(event.category)
                }

                _state.value = _state.value.copy(
                    chosenCategories = currentCategories
                )
            }
        }
    }

    fun getNews() {
        val date = getActualDate()
        viewModelScope.launch {
            val allNews = mutableListOf<News>()
            val topNews = mutableListOf<News>()

            try {
                val results = _state.value.categories.map { genre ->
                    async {
                        try {
                            useCase(genre).onEach { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        val newsList = result.data ?: emptyList()
                                        if (genre == "top") {
                                            topNews.addAll(newsList)
                                        } else {
                                            allNews.addAll(newsList)
                                        }
                                        _state.value = _state.value.copy(
                                            isLoading = false,
                                            date = date
                                        )
                                    }
                                    is Resource.Error -> {
                                        _state.value = _state.value.copy(
                                            error = result.message ?: "Unexpected error occurred",
                                            date = date,
                                            isLoading = false
                                        )
                                    }
                                    is Resource.Loading -> {
                                        _state.value = _state.value.copy(
                                            isLoading = true,
                                            date = date
                                        )
                                    }
                                }
                            }.toList()
                        } catch (e: HttpException) {
                            if (e.code() == 429) {
                                _state.value = _state.value.copy(
                                    error = "Превышен лимит запросов. Пожалуйста, попробуйте позже.",
                                    isLoading = false
                                )
                            } else {
                                _state.value = _state.value.copy(
                                    error = "Ошибка сети: ${e.localizedMessage}",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
                results.awaitAll()
                _state.value = _state.value.copy(
                    news = if (topNews.isEmpty()) emptyList() else topNews,
                    allNews = if (allNews.isEmpty()) emptyList() else allNews,
                    date = date,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка при загрузке новостей: ${e.localizedMessage}",
                    isLoading = false
                )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
