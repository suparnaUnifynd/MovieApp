package com.example.movieapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.use_cases.GetMovieUseCasesWrapper

class MovieDetailViewModelFectory(
    private val app: Application,
    private val getMovieUseCasesWrapper: GetMovieUseCasesWrapper
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(app, getMovieUseCasesWrapper ) as T
    }
}