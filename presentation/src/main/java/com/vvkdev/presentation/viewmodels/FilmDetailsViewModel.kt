package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.repository.FilmRepository
import com.vvkdev.presentation.base.state.BaseStateViewModel
import com.vvkdev.presentation.base.state.UiState
import com.vvkdev.presentation.fragments.FilmDetailsFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val repository: FilmRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseStateViewModel<Film>() {

    private val filmId: Int = FilmDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).filmId

    init {
        loadFilm(forceRefresh = false)
    }

    override fun retry() = loadFilm(forceRefresh = true)

    fun forceRefresh() = loadFilm(forceRefresh = true)

    private fun loadFilm(forceRefresh: Boolean) {
        viewModelScope.launch {
            updateState(UiState.Loading)
            updateState(
                when (val result = repository.getFilmById(filmId, forceRefresh)) {
                    is LoadResult.Success -> UiState.Success(result.data)
                    is LoadResult.Error -> UiState.Error(result.message)
                }
            )
        }
    }
}
