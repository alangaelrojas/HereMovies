package com.alan.heremovies.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.repository.MoviesRepository
import com.alan.heremovies.sys.di.components.DaggerRepositoryComponent
import com.alan.heremovies.sys.utils.Status
import javax.inject.Inject

class MoviesViewModel : ViewModel() {

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }
    @Inject lateinit var repository: MoviesRepository

    //region :: VALS
    private var pageCountPopular = 1
    private var pageCountTopRated = 1
    private var pageCountSearch = 1
    //endregion

    //region :: LIVEDATAS
    val topRated by lazy { MutableLiveData<List<Movie>>() }
    val popular by lazy { MutableLiveData<List<Movie>>() }
    val searches by lazy { MutableLiveData<List<Movie>>() }

    val errorPopular by lazy { MutableLiveData<Status>() }
    val errorTopRated by lazy { MutableLiveData<Status>() }
    val errorSearches by lazy { MutableLiveData<Status>() }
    //endregion


    fun getPopularMovies(){
        repository.getPopularMovies(pageCountPopular, buildPopularSuccessObserver(), errorPopularObserver())
    }

    fun getTopRatedMovies(){
        repository.getTopRatedMovies(pageCountTopRated, buildTopRatedSuccessObserver(), errorTopRatedObserver())
    }

    fun getMoviesBySearch(query: String, adult: Boolean){
        repository.getMoviesBySearch(query, pageCountSearch, adult, buildSearchSuccessObserver(), errorSearchObserver())
    }

    //region :: OBSERVERS
    private fun buildPopularSuccessObserver(): Observer<List<Movie>> {
        return Observer {
            if (it.isNotEmpty())
                popular.value = it.sortedByDescending { list -> list.popularity }
        }
    }
    private fun errorPopularObserver(): Observer<Status> {
        return Observer {
            errorPopular.value = it
        }
    }

    private fun buildTopRatedSuccessObserver(): Observer<List<Movie>> {
        return Observer {
            if (it.isNotEmpty()) topRated.value = it.sortedByDescending { list -> list.voteAverage }
        }
    }
    private fun errorTopRatedObserver(): Observer<Status> {
        return Observer {
            errorTopRated.value = it
        }
    }

    private fun buildSearchSuccessObserver(): Observer<List<Movie>> {
        return Observer {
            if (it.isNotEmpty()) searches.value = it.sortedBy { list -> list.title }
            else errorPopular.value = Status(404, "No encontrados")
        }
    }
    private fun errorSearchObserver(): Observer<Status> {
        return Observer {
            errorPopular.value = it
        }
    }
    //endregion
}