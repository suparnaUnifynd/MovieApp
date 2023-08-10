package com.example.movieapp.presentation.viewModel


import NetworkResponse
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Constants
import com.example.movieapp.UIState
import com.example.movieapp.Utils.isNetworkAvailable
import com.example.movieapp.data.model.MovieDetailDto
import com.example.movieapp.data.model.MovieListDto
import com.example.movieapp.domain.use_cases.GetMovieUseCasesWrapper
import com.example.movieapp.domain.use_cases.MovieUseCasesWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val app: Application,
    private val useCasesWrapper: GetMovieUseCasesWrapper) :
    ViewModel() {
    private val TAG = this::class.java.simpleName

    private val uiStateChannel = Channel<UIState>(Channel.BUFFERED)
    val uiStateFlow = uiStateChannel.receiveAsFlow()

    val movieDetail: MutableLiveData<NetworkResponse<MovieDetailDto>> = MutableLiveData()


    fun getMovieDetail(
        id: Int,
    ) {
        viewModelScope.launch {
//            movieDetail.postValue(NetworkResponse.Loading())

//            try {
//                val response = useCasesWrapper.getMovieDetail(id.toString())
//                when(response){
//                    is NetworkResponse.Success->{
//                        movieDetail.postValue(response)
//                        Log.d("MainActivity","movieList ****  ${movieDetail} ")
//
//                    }
//                    is NetworkResponse.Error->{
//
//                    }
//                    is NetworkResponse.Loading->{
//
//                    }
//
//                    else -> {}
//                }
//
//
//            } catch (e: Exception) {
//                Log.d("MainActivity","movieList response  ${e.message.toString()} ")
//                movieDetail.postValue(NetworkResponse.Error(e.message.toString()))
//            }

//            viewModelScope.launch { uiStateChannel.send(UIState.Loading) }
//            val response = useCasesWrapper.getMovieDetail(id.toString())
//            when (response) {
//                is NetworkResponse.Success -> {
//                    uiStateChannel.send(UIState.Success(response.data))
////
////                    if (response.data != 200) {
////                    } else {
////                        uiStateChannel.send(UIState.Success(response.body.data))
////                        Timber.d("Spin Wheel data: ${response.body.data.spinWheels}")
////                    }
//                }
//
//                is NetworkResponse.Error -> {
//                    uiStateChannel.send(UIState.Error("Server Error"))
//                    Timber.d("Server Error")
//                }
//
//
//                else -> {}
//            }
//        }
        }
    }

//    val movies: MutableLiveData<NetworkResponse<MovieListDto>> = MutableLiveData()


        fun getMovieDetail1(id: Int) = viewModelScope.launch(Dispatchers.IO) {
            movieDetail.postValue(NetworkResponse.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val response = useCasesWrapper.getMovieDetail(id.toString())

                    // Check if the deserialization is successful
                    if (response.body() != null) {
                        // Update LiveData with successful response
                        movieDetail.postValue(NetworkResponse.Success(response.body()!!))
                    } else {
                        // Handle empty response
                        movieDetail.postValue(NetworkResponse.Error("Empty response"))
                    }

                } else {
                    movieDetail.postValue(NetworkResponse.Error(Constants.NO_INTERNET_MSG))
                }

            } catch (e: Exception) {
                Log.d("MainActivity", "movieDetail response  ${e.message.toString()} ")
                movieDetail.postValue(NetworkResponse.Error(e.message.toString()))
            }
        }

    }