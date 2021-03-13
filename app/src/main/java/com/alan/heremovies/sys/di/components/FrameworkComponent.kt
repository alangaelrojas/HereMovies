package com.alan.heremovies.sys.di.components

import com.alan.heremovies.data.datasource.memory.MoviesMemoryDS
import com.alan.heremovies.data.datasource.web.MovieWebDS
import com.alan.heremovies.sys.di.modules.FrameworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FrameworkModule::class])
interface FrameworkComponent {

    fun inject(moviesWebDS: MovieWebDS)
    fun inject(moviesMemoryDS: MoviesMemoryDS)
}