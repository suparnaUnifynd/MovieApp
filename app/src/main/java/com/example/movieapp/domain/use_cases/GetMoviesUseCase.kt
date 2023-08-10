package com.example.movieapp.domain.use_cases

import NetworkResponse
import com.example.movieapp.data.model.MovieListDto
import com.example.movieapp.domain.repository.MovieRepository

class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(pageNo:Int): NetworkResponse<MovieListDto> = movieRepository.getMovies(pageNo)
}