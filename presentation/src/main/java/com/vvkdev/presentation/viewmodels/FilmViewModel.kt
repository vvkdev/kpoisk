package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.model.Film
import com.vvkdev.domain.repository.FilmsRepository
import com.vvkdev.presentation.base.state.BaseStateViewModel
import com.vvkdev.presentation.base.state.UiState
import com.vvkdev.presentation.fragments.FilmFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val repository: FilmsRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseStateViewModel<Film>() {

    private val filmId: Int = FilmFragmentArgs.fromSavedStateHandle(savedStateHandle).filmId

    init {
        loadFilm()
    }

    override fun retry() {
        loadFilm()
    }

    private fun loadFilm() {
        viewModelScope.launch {
            updateState(UiState.Loading)
            updateState(
                when (val result = repository.getFilmById(filmId)) {
                    is LoadResult.Success -> UiState.Success(result.data)
                    is LoadResult.Error -> UiState.Error(result.message)
                }
            )
        }
    }
}
