package com.vvkdev.presentation.screens.film

import com.vvkdev.domain.model.Film

sealed class FilmState {
    object Loading : FilmState()
    data class Success(val data: Film) : FilmState()
    data class Error(val message: String) : FilmState()
}
