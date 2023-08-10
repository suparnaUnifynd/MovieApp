package com.example.movieapp.presentation.viewModel

import NetworkResponse
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import com.example.movieapp.*
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieListDto
import com.example.movieapp.domain.use_cases.MovieUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val app: Application,
    private val useCasesWrapper: MovieUseCasesWrapper

    ) : AndroidViewModel(app) {

    val movies: MutableLiveData<NetworkResponse<MovieListDto>> = MutableLiveData()

//    private var periodicWorkRequest: PeriodicWorkRequest? = null


    fun getMovies(pageNo:Int) = viewModelScope.launch(Dispatchers.IO) {
        movies.postValue(NetworkResponse.Loading())
        try {
            if (Utils.isNetworkAvailable(app)) {
                val response = useCasesWrapper.getMoviesUseCase.execute(pageNo)
                Log.d("MainActivity","movieList response success  ${response.message} ")
                Log.d("MainActivity","movieList response success  ${response.data} ")
                Log.d("MainActivity","movieList response success  ${response} ")

                movies.postValue(response)
                response.data?.let { saveMovies(it.results) }
            } else {
                movies.postValue(NetworkResponse.Error(Constants.NO_INTERNET_MSG))
            }

        } catch (e: Exception) {
            Log.d("MainActivity","movieList response  ${e.message.toString()} ")
            movies.postValue(NetworkResponse.Error(e.message.toString()))
        }
    }

//    fun startMovieUpdates() {
//        periodicWorkRequest = PeriodicWorkRequest.Builder(
//            MovieSyncWorker::class.java, 30, TimeUnit.MINUTES
//        ).build()
//
//        WorkManager.getInstance().enqueueUniquePeriodicWork(
//            "MovieUpdateWork",
//            ExistingPeriodicWorkPolicy.KEEP,
//            periodicWorkRequest!!
//        )
//    }

//    fun stopMovieUpdates() {
//        periodicWorkRequest?.let {
//            WorkManager.getInstance().cancelWorkById(it.id)
//        }
//    }




    fun saveMovies(movies: List<Movie>) = viewModelScope.launch {
        useCasesWrapper.saveMoviesUseCase.execute(movies)
    }


    fun getSavedMovies() = liveData {
        useCasesWrapper.getSavedMoviesUseCase.execute().collect {
            emit(it)
        }
    }

}