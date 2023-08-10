package com.example.movieapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.Constants
import com.example.movieapp.R
import com.example.movieapp.Utils.getYearFromDate
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ListItemMovieBinding


class MovieListingAdapter(
    private val list: ArrayList<Movie> = arrayListOf(),
    private val interaction: Interaction? = null) :

    RecyclerView.Adapter<MovieListingAdapter.MovieViewHolder>() {
    private lateinit var  binding : ListItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding=DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item_movie,
            parent,
            false
        )
        return MovieViewHolder(binding ,interaction)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(data: List<Movie>) {
        data.forEach {
            if(!list.contains(it)){
                list.add(it)
            }
        }
        notifyItemRangeInserted(list.size, data.size)
    }

    fun clearAllData() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = list[position]
        return holder.bind(item)
    }

    inner class MovieViewHolder(
        val binding: ListItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            var url= Constants.IMAGE_BASE_URL+item.posterPath
            Glide.with(binding.root.context).load(url).into(binding.ivMovie)
            binding.tvMovie.text=item.title
            binding.tvYear.text=getYearFromDate(item.releaseDate)
            binding.root.setOnClickListener {
                interaction?.onMovieClicked(position, item)
            }
        }
    }
    interface Interaction {
        fun onMovieClicked(position: Int, item: Movie)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}