package com.example.movieapp.presentation.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Constants
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.presentation.adapter.MovieListingAdapter
import com.example.movieapp.presentation.viewModel.MovieViewModel
import com.example.movieapp.presentation.viewModel.MovieViewModelFectory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener,MovieListingAdapter.Interaction {
    private lateinit var binding : ActivityMainBinding
    @Inject
    lateinit var factory : MovieViewModelFectory
    private lateinit var viewModel: MovieViewModel
    lateinit var movieListingAdapter: MovieListingAdapter
    private var currentPage = 1
    private var totalPages = 100
    private var isScrollDataLoading = false
    private var gridLayoutManager: GridLayoutManager?=null
    private var movieList: List<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeVariables()
        registerScrollListener()
        setUpListeners()
        setUpAdapters()
    }

    override fun onResume() {
        super.onResume()
        resetListParams()
        getSavedMovies()
    }
    private fun initializeVariables(){
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
       // viewModel.startMovieUpdates()

    }
    private fun setUpListeners(){
        binding.ivClose.setOnClickListener(this)
    }
    private fun setUpAdapters(){
        gridLayoutManager=GridLayoutManager(
            this,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape mode
            gridLayoutManager!!.spanCount = 6
        } else {
            // Portrait mode
            gridLayoutManager!!.spanCount = 3
        }
        movieListingAdapter= MovieListingAdapter(interaction = this@MainActivity)
        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.adapter = movieListingAdapter
    }


    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ivClose ->{
                this.onBackPressed()
            }
        }
    }

    override fun onMovieClicked(position: Int, item: Movie) {
        MovieDetailActivity.startActivity(this,item.id.toString())
    }

    private fun resetListParams(){
        currentPage = 1
        totalPages = 100
        isScrollDataLoading = false
        movieListingAdapter.clearAllData()
    }
    private fun registerScrollListener() {
        binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("MainActivity","movieList ** ${currentPage} ")
                Log.d("MainActivity","movieList ** ${totalPages} ")
                Log.d("MainActivity","movieList ** ${currentPage<totalPages} ")

                if (!binding.rvMovieList.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    !isScrollDataLoading &&
                    (currentPage < totalPages)
                ) {
                    currentPage++
                    isScrollDataLoading = true
                    viewModel.getMovies(currentPage)
                }
                //show shadow
                if(gridLayoutManager!!.findFirstCompletelyVisibleItemPosition()==0){
                    binding.ivShadow.visibility=View.GONE
                }else{
                    binding.ivShadow.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun getMovies() {
        viewModel.getMovies(currentPage)
        viewModel.movies.observe(this) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        movieList = it.results
                        this.totalPages=it.totalPages
                        isScrollDataLoading = false
                        movieListingAdapter.addData(movieList)
                    }
                    Log.d("MainActivity","movieList ** ${totalPages} ")
                    Log.d("MainActivity","movieList ** ${movieList.size} ")
                }
                is NetworkResponse.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        if (it.equals(Constants.NO_INTERNET_MSG)) {
                            Timber.d("Internet not available show local data")
                            getSavedMovies()
                        }else Timber.d("Error msg: $it")
                    }
                }

                is NetworkResponse.Loading -> {
                    viewModel.getSavedMovies().observe(this) {
                        if (it.isNullOrEmpty()) {
                            showProgressBar()
                        } else {
                            hideProgressBar()
                            movieListingAdapter.addData(it)
                        }
                    }

                }

                else -> {}
            }
        }
    }


    private fun getSavedMovies() {
        viewModel.getSavedMovies().observe(this) {
            it?.let {
                movieList = it
                if(movieList.size==0){
                    getMovies()
                }else{
                    movieListingAdapter.addData(movieList)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewModel.stopMovieUpdates()
    }
}