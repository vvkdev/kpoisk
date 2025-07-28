package com.vvkdev.presentation.viewholders

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.vvkdev.presentation.databinding.ItemFilmBinding
import com.vvkdev.presentation.models.FilmItem

class FilmItemViewHolder(private val binding: ItemFilmBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(film: FilmItem) {
        with(binding) {
            nameTextView.text = film.name
            ratingTextView.text = film.rating
            foreignNameTextView.text = film.foreignName
            foreignNameTextView.isGone = film.foreignName.isBlank()
            timeTextView.text = film.time
            countriesTextView.text = film.countries
            genresTextView.text = film.genres
        }
    }
}
