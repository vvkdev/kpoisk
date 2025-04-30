package com.vvkdev.presentation.screens.film

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.repository.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: FilmsRepository
) : ViewModel() {

    private val args = FilmDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val filmId: Int = args.filmId

    private val _state = MutableStateFlow<FilmState>(FilmState.Loading)
    val state: StateFlow<FilmState> = _state

    init {
        loadFilm()
    }

    fun retry() {
        loadFilm()
    }

    private fun loadFilm() {
        viewModelScope.launch {
            _state.value = FilmState.Loading
            _state.value = when (val result = repository.getFilmById(filmId)) {
                is LoadResult.Success -> FilmState.Success(result.data)
                is LoadResult.Error -> FilmState.Error(result.message)
            }
        }
    }
}


