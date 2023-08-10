package com.example.movieapp.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.R
import com.example.movieapp.data.model.MovieDetailDto
import com.example.movieapp.databinding.ListItemGenreBinding


class GenresChipAdapter(private val interaction: Interaction? = null) :
    ListAdapter<MovieDetailDto.Genre, GenresChipAdapter.ViewHolder>(
        CouponDC()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemGenreBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item_genre,
            parent,
            false)

        return ViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position),position)

    fun swapData(data: List<MovieDetailDto.Genre>) {
        submitList(data.toMutableList())
    }

    inner class ViewHolder(
        val binding: ListItemGenreBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: MovieDetailDto.Genre, position: Int){
            binding.tvChip.text=item.name

        }
    }

    interface Interaction {
        fun onCouponClicked(position: Int, item: MovieDetailDto.Genre)
    }

    private class CouponDC : DiffUtil.ItemCallback<MovieDetailDto.Genre>()
    {
        override fun areItemsTheSame(
            oldItem: MovieDetailDto.Genre,
            newItem: MovieDetailDto.Genre
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieDetailDto.Genre,
            newItem: MovieDetailDto.Genre
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}