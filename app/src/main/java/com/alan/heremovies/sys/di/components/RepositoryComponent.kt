package com.alan.heremovies.sys.di.components

import com.alan.heremovies.sys.di.modules.RepositoryModule
import com.alan.heremovies.ui.movie_detail.DetailMovieViewModel
import com.alan.heremovies.ui.movies.MoviesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun inject(moviesViewModel: MoviesViewModel)
    fun inject(detailMovieViewModel: DetailMovieViewModel)
}