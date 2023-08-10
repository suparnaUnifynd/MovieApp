package com.example.movieapp.domain.use_cases

import com.example.movieapp.data.model.Movie
import com.example.movieapp.domain.repository.MovieRepository


class SaveMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(movies: List<Movie>) = movieRepository.saveMovies(movies)
}