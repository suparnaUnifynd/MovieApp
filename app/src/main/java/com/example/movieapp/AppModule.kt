package com.example.movieapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.api.ApiClient
import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.db.MovieDao
import com.example.movieapp.data.db.MovieDatabase
import com.example.movieapp.data.repository.dataSource.MovieLocalDataSource
import com.example.movieapp.data.repository.dataSource.MovieRemoteDataSource
import com.example.movieapp.data.repository.dataSource.MovieRepositoryImpl
import com.example.movieapp.data.repository.dataSourceImpl.MovieLocalDataSourceImpl
import com.example.movieapp.data.repository.dataSourceImpl.MovieRemoteDataSourceImpl
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.use_cases.*
import com.example.movieapp.presentation.viewModel.MovieDetailViewModelFectory
import com.example.movieapp.presentation.viewModel.MovieViewModelFectory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(

        @ApplicationContext
        context: Context

    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiClient.makeOkHttpClient())
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_db")
                .fallbackToDestructiveMigration()
                .build()

    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.getMovieDao()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        apiService: ApiService
    ):MovieRemoteDataSource{
        return MovieRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource

    ): MovieRepository {
        return MovieRepositoryImpl(
            movieLocalDataSource,
            movieRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideMovieUseCases(
        movieRepository: MovieRepository
    ): MovieUseCasesWrapper {
        return MovieUseCasesWrapper(
            getMoviesUseCase= GetMoviesUseCase(movieRepository),
            getSavedMoviesUseCase = GetSavedMoviesUseCase(movieRepository),
            saveMoviesUseCase = SaveMoviesUseCase(movieRepository),
            deleteAllUseCase = DeleteAllMovieCase(movieRepository)
        )
    }


    @Singleton
    @Provides
    fun provideMovieViewModelFactory(
        application: Application,
        movieUseCasesWrapper: MovieUseCasesWrapper
    ):MovieViewModelFectory{
        return MovieViewModelFectory(
            application,
            movieUseCasesWrapper
        )
    }

    @Singleton
    @Provides
    fun provideGetMovieUseCasesWrapper(
        apiService: ApiService
    ): GetMovieUseCasesWrapper {
        return GetMovieUseCasesWrapper(
            getMovieDetail = GetMovieDetail(apiService)
        )
    }

    @Singleton
    @Provides
    fun provideMovieDetailViewModelFactory(
        application: Application,
        getMovieUseCasesWrapper: GetMovieUseCasesWrapper
    ):MovieDetailViewModelFectory{
        return MovieDetailViewModelFectory(
            application,
            getMovieUseCasesWrapper
        )
    }
}