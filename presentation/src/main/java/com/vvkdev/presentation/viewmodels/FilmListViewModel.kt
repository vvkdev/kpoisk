package com.vvkdev.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vvkdev.domain.LoadResult
import com.vvkdev.domain.models.FilmShort
import com.vvkdev.domain.repositories.FilmRepository
import com.vvkdev.presentation.base.BaseViewModel
import com.vvkdev.presentation.base.UiState
import com.vvkdev.presentation.fragments.FilmListFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    val filmName: String = FilmListFragmentArgs.fromSavedStateHandle(savedStateHandle).filmName

    private val _uiState = MutableStateFlow<UiState<List<FilmShort>>>(UiState.Default)
    val uiState: StateFlow<UiState<List<FilmShort>>> = _uiState

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = filmRepository.findByName(filmName)) {
                is LoadResult.Success -> UiState.Success(result.data)
                is LoadResult.Error -> UiState.Error(result.message)
            }
        }
    }
}
