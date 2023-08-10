package com.example.movieapp.data.db

import androidx.room.*
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

}