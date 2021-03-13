package com.alan.heremovies.repository

import android.content.Context
import androidx.lifecycle.Observer
import com.alan.heremovies.BuildConfig
import com.alan.heremovies.MainApp
import com.alan.heremovies.data.datasource.memory.MoviesMemoryDS
import com.alan.heremovies.data.datasource.web.MovieWebDS
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.data.models.VideoResult
import com.alan.heremovies.sys.di.components.DaggerDataSourceComponent
import com.alan.heremovies.sys.utils.Constants
import com.alan.heremovies.sys.utils.Status
import com.alan.heremovies.sys.utils.hasConnection
import javax.inject.Inject

class MoviesRepository {

    private var context: Context = MainApp.getUtils().getContext()

    init {
        DaggerDataSourceComponent.builder().build().inject(this)
    }
    @Inject lateinit var webDS: MovieWebDS
    @Inject lateinit var memoryDS: MoviesMemoryDS

    fun getPopularMovies(page: Int, observer: Observer<List<Movie>>, error: Observer<Status>){
        if (context.hasConnection()) webDS.getPopularMovies(apiKey(), languague(), page, buildPopularSuccessObserver(observer) , error)
        else memoryDS.getMovies(Constants.MOVIE_POPULAR, observer, error)
    }
    fun getTopRatedMovies(page: Int, observer: Observer<List<Movie>>, error: Observer<Status>){

        if (context.hasConnection()) webDS.getTopRatedMovies(apiKey(), languague(), page, buildTopRatedSuccessObserver(observer), error)
        else memoryDS.getMovies(Constants.MOVIE_TOPRATED, observer, error)

    }
    fun getRecommendations(page: Int, movieId: Int, observer: Observer<List<Movie>>, error: Observer<Status>){

        if (context.hasConnection()) webDS.getMoviesRecommedation(apiKey(), languague(), page, movieId, buildRecommendationSuccessObserver(movieId, observer), error)
        else memoryDS.getMoviesRecommendation(Constants.MOVIE_RECOMMENDATION, movieId,  observer, error)

    }
    fun getMoviesBySearch(query: String, page: Int, adult: Boolean, observer: Observer<List<Movie>>, error: Observer<Status>){
        if (context.hasConnection()) webDS.getMoviesBySearch(apiKey(), languague(), query,  page, adult, observer , error)
        else memoryDS.getMoviesBySearch(query, observer, error)
    }

    fun getVideoMovie(movieId: Int, observer: Observer<VideoResult>, error: Observer<Status>){
        if (context.hasConnection()) webDS.getVideoMovie(apiKey(), languague(), movieId, observer, error)
        else error.onChanged(Status(404, "Video unavailable"))
    }

    //Local

    //Private functions
    private fun apiKey(): String = BuildConfig.API_KEY
    private fun languague(): String = Constants.MOVIE_LANG

    //region :: OBSERVERS
    private fun buildPopularSuccessObserver(observer: Observer<List<Movie>>): Observer<List<Movie>> {
        return Observer {
            memoryDS.insertMovies(it, Constants.MOVIE_POPULAR)
            observer.onChanged(it)
        }
    }

    private fun buildTopRatedSuccessObserver(observer: Observer<List<Movie>>): Observer<List<Movie>> {
        return Observer {
            memoryDS.insertMovies(it, Constants.MOVIE_TOPRATED)
            observer.onChanged(it)
        }
    }

    private fun buildRecommendationSuccessObserver(idReco: Int, observer: Observer<List<Movie>>): Observer<List<Movie>> {
        return Observer {
            memoryDS.insertMoviesRecommendations(idReco, it, Constants.MOVIE_RECOMMENDATION)
            observer.onChanged(it)
        }
    }
    //endregion
}