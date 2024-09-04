package com.nikitacherenkov.newsapp.presentation.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitacherenkov.newsapp.domain.use_case.get_news.GetNewsUseCase
import com.nikitacherenkov.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCase: GetNewsUseCase
): ViewModel(){
    private val _state = mutableStateOf(MainScreenState())
    val state : State<MainScreenState> = _state


    fun getActualDate(){
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())
        val formattedDate = today.format(formatter)
        _state.value = MainScreenState(
            date = formattedDate
        )
    }

    fun getNews(){
        useCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = MainScreenState(
                        news =  result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = MainScreenState(
                        error = result.message ?: "Unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MainScreenState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}