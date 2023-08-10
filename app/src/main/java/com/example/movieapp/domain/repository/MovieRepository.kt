package com.example.movieapp.domain.repository

import NetworkResponse
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieListDto
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getMovies(pageNo:Int): NetworkResponse<MovieListDto>
    suspend fun saveMovies(user: List<Movie>)
    fun getSavedMovies(): Flow<List<Movie>>
    suspend fun deleteAll()
}