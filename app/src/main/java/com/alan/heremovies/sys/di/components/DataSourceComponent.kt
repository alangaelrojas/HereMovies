package com.alan.heremovies.sys.di.components

import com.alan.heremovies.repository.MoviesRepository
import com.alan.heremovies.sys.di.modules.DataSourceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface DataSourceComponent {

    fun inject(repository: MoviesRepository)
}