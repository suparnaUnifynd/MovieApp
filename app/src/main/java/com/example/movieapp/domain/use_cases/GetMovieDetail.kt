package com.example.movieapp.domain.use_cases

import NetworkResponse
import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.model.MovieDetailDto
import retrofit2.Response

class GetMovieDetail (private val apiService: ApiService){
    suspend operator fun invoke(id: String): Response<MovieDetailDto> {
        return apiService.getMovieById(id)
    }

}