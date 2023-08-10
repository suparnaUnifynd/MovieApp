package com.example.movieapp.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.Constants
import com.example.movieapp.R
import com.example.movieapp.Utils
import com.example.movieapp.data.model.MovieDetailDto
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.presentation.adapter.GenresChipAdapter
import com.example.movieapp.presentation.viewModel.MovieDetailViewModel
import com.example.movieapp.presentation.viewModel.MovieDetailViewModelFectory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding : ActivityMovieDetailBinding
    @Inject
    lateinit var factory : MovieDetailViewModelFectory
    private var movieId=""
    private lateinit var viewModel: MovieDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        initializeVariables()
        setUpListeners()
        setUpViewModel()
    }
    companion object {
        fun startActivity(context: Context, movieId: String) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieId", movieId)
            context.startActivity(intent)
        }
    }
    private fun initializeVariables(){
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(MovieDetailViewModel::class.java)
    }
    override fun onResume() {
        super.onResume()
        if (null != intent?.getStringExtra("movieId")) {
            movieId = intent.getStringExtra("movieId")!!
            getMovieDetail()
        }
    }
    private fun getMovieDetail() {
        if (movieId.isNotBlank()) viewModel.getMovieDetail1(movieId.toInt())
    }
    private fun setInitView(data: MovieDetailDto) {
        binding.ivClose.setOnClickListener(this)
        binding.tvMovieName.text=data.title
        binding.txtMovieDetail.text=data.overview
        binding.tvDuration.text=data.runtime.toString()+" mins"
        binding.tvYear.text= Utils.getYearFromDate(data.releaseDate)
        Glide.with(binding.root.context).load(Constants.IMAGE_BASE_URL+data.posterPath).into(binding.imgMoviePic)

        val genresChipAdapter= GenresChipAdapter()
        binding.rvGenreList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvGenreList.adapter = genresChipAdapter
        genresChipAdapter.swapData(data.genres)
    }

    private fun setUpViewModel(){
        viewModel.movieDetail.observe(this) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    hideProgressBar()
                    response.data?.let {
                            setInitView(response.data)
                    }
                }
                is NetworkResponse.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        if (it.equals(Constants.NO_INTERNET_MSG)) {
                            Timber.d("Internet not available show local data")
                        }else Timber.d("Error msg: $it")
                    }
                }

                is NetworkResponse.Loading -> {
                    viewModel.movieDetail.observe(this) {
                        if (it.data==null) {
                            showProgressBar()
                        } else {
                            hideProgressBar()
                        }
                    }
                }

                else -> {}
            }
        }
    }
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun setUpListeners(){
        binding.ivClose.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ivClose ->{
                this.onBackPressed()
            }
        }
    }
}