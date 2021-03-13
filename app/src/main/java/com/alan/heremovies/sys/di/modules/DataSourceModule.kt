package com.alan.heremovies.sys.di.modules

import com.alan.heremovies.data.datasource.memory.MoviesMemoryDS
import com.alan.heremovies.data.datasource.web.MovieWebDS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providesMoviesWebDS(): MovieWebDS = MovieWebDS()


    @Provides
    @Singleton
    fun providesMoviesMemoryDS(): MoviesMemoryDS = MoviesMemoryDS()

}