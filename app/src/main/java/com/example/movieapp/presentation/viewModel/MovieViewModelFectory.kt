package com.example.movieapp.presentation.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.use_cases.MovieUseCasesWrapper

class MovieViewModelFectory(
    private val app: Application,
    private val useCasesWrapper: MovieUseCasesWrapper
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(app, useCasesWrapper ) as T
    }
}