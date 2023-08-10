package com.example.movieapp.domain.use_cases

import com.example.movieapp.domain.repository.MovieRepository

class DeleteAllMovieCase(private val movieRepository: MovieRepository) {

    suspend fun execute()= movieRepository.deleteAll()
}