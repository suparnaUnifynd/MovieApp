package com.example.movieapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieapp.domain.repository.MovieRepository

class MovieSyncWorker(context: Context,
                      params: WorkerParameters, private val repository: MovieRepository)
    : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        try {
//            val movieData =repository.getMovies( )
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
        return Result.success()
    }


}
