package com.example.movieapp.domain.use_cases

data class MovieUseCasesWrapper (
    val getMoviesUseCase: GetMoviesUseCase,
    val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    val saveMoviesUseCase: SaveMoviesUseCase,
    val deleteAllUseCase: DeleteAllMovieCase
)