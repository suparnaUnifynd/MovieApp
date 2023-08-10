package com.example.movieapp.data.api

import NetworkResponse
import com.example.movieapp.Constants
import com.example.movieapp.data.model.MovieDetailDto
import com.example.movieapp.data.model.MovieListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getMovies(
        @Query("page") page: Int=1,
        @Query("language") language: String="en-US",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ):Response<MovieListDto>


    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieDetailDto>
}

