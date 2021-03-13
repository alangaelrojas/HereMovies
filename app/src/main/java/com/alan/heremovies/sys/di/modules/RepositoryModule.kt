package com.alan.heremovies.sys.di.modules

import com.alan.heremovies.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesMoviesRepository(): MoviesRepository = MoviesRepository()

}