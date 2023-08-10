package com.example.movieapp.data.repository.dataSource

import NetworkResponse
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieListDto
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MovieRepositoryImpl(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
): MovieRepository {

    override suspend fun getMovies(pageNo:Int): NetworkResponse<MovieListDto> {
        return sendResponse(movieRemoteDataSource.getMovies(pageNo))
    }

    override suspend fun saveMovies(Movie: List<Movie>) {
        movieLocalDataSource.saveMoviesToDB(Movie)
    }

    override fun getSavedMovies(): Flow<List<Movie>> {
        return movieLocalDataSource.getSavedMovies()

    }

    override suspend fun deleteAll() {
        movieLocalDataSource.deleteALl()
    }
    

    private fun sendResponse(response:Response<MovieListDto>):NetworkResponse<MovieListDto>{
        if(response.isSuccessful){
            response.body()?.let { result->
                return NetworkResponse.Success(result)
            }
        }
        return NetworkResponse.Error(response.message())
    }
}