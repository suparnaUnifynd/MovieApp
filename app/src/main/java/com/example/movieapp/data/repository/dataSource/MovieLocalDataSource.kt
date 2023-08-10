package com.example.movieapp.data.repository.dataSource

import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun saveMoviesToDB(Movies: List<Movie>)
    fun getSavedMovies(): Flow<List<Movie>>
//    suspend fun deleteMovieFromDB(Movie: Movie)
    suspend fun deleteALl()
}