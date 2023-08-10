package com.example.movieapp.data.repository.dataSourceImpl

import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.model.MovieListDto
import com.example.movieapp.data.repository.dataSource.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(private val apiService: ApiService): MovieRemoteDataSource {
    override suspend fun getMovies(pageNo:Int): Response<MovieListDto> {
        return apiService.getMovies(pageNo)
    }
}