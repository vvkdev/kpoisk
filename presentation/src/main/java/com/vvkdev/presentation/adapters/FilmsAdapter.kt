package com.vvkdev.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vvkdev.presentation.databinding.ItemFilmBinding
import com.vvkdev.presentation.models.FilmItem
import com.vvkdev.presentation.viewholders.FilmItemViewHolder

internal class FilmsAdapter(private val onRootClickListener: (FilmItem) -> Unit) :
    RecyclerView.Adapter<FilmItemViewHolder>() {

    var items: List<FilmItem> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return FilmItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        val film = items[position]
        holder.bind(film)
        holder.itemView.setOnClickListener { onRootClickListener(film) }
    }

    override fun getItemCount() = items.size
}
