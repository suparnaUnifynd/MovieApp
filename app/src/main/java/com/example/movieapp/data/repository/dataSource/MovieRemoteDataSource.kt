package com.example.movieapp.data.repository.dataSource

import com.example.movieapp.data.model.MovieListDto
import retrofit2.Response


interface MovieRemoteDataSource {
    suspend fun getMovies(pageNo:Int): Response<MovieListDto>
}