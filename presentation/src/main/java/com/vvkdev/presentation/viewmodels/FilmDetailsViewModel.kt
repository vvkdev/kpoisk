package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.repository.FilmRepository
import com.vvkdev.presentation.base.BaseViewModel
import com.vvkdev.presentation.base.UiState
import com.vvkdev.presentation.fragments.FilmDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val filmId: Int = FilmDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).filmId

    private val _uiState = MutableStateFlow<UiState<Film>>(UiState.Default)
    val uiState: StateFlow<UiState<Film>> = _uiState

    init {
        loadFilm(forceRefresh = false)
    }

    fun loadFilm(forceRefresh: Boolean) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = filmRepository.getFilmById(filmId, forceRefresh)) {
                is LoadResult.Success -> UiState.Success(result.data)
                is LoadResult.Error -> UiState.Error(result.message)
            }
        }
    }
}
