package com.example.movieapp.domain.use_cases

import com.example.movieapp.data.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetSavedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(): Flow<List<Movie>> = movieRepository.getSavedMovies()
}