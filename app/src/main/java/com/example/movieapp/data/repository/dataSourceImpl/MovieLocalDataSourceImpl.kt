package com.example.movieapp.data.repository.dataSourceImpl

import com.example.movieapp.data.db.MovieDao
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.dataSource.MovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(private val movieDao: MovieDao): MovieLocalDataSource {

    override suspend fun saveMoviesToDB(movies: List<Movie>) {
        movieDao.saveMovies(movies)

    }
    override fun getSavedMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }

    override suspend fun deleteALl() {
        movieDao.deleteAll()
    }
}