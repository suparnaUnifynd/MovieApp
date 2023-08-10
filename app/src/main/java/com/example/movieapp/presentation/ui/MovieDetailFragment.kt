package com.example.movieapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.Constants
import com.example.movieapp.R
import com.example.movieapp.Utils
import com.example.movieapp.data.model.Movie

import com.example.movieapp.databinding.FragmentMovieDetailBinding


class MovieDetailFragment (
    private val movie: Movie): DialogFragment(),View.OnClickListener{
    private lateinit var binding : FragmentMovieDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen)
        enterTransition=0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMovieDetailBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.ivClose ->{
                this.dismiss()
            }
        }
    }
}