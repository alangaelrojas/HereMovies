package com.alan.heremovies.data.datasource.web

import androidx.lifecycle.Observer
import com.alan.heremovies.MainApp
import com.alan.heremovies.data.datasource.web.api.Services
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.data.models.MoviesResponse
import com.alan.heremovies.data.models.VideoResponse
import com.alan.heremovies.data.models.VideoResult
import com.alan.heremovies.sys.di.components.DaggerFrameworkComponent
import com.alan.heremovies.sys.di.modules.ContextModule
import com.alan.heremovies.sys.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieWebDS {

    init {
        DaggerFrameworkComponent.builder()
            .contextModule(ContextModule(MainApp.getUtils().getContext()))
            .build()
            .inject(this)
    }

    @Inject lateinit var services: Services

    private lateinit var callPopularMovies: Call<MoviesResponse>
    private lateinit var callTopRatedMovies: Call<MoviesResponse>
    private lateinit var callMoviesBySearch: Call<MoviesResponse>
    private lateinit var callMoviesRecommendation: Call<MoviesResponse>
    private lateinit var callVideo: Call<VideoResponse>


    fun getPopularMovies(apiKey: String, language: String, page: Int, observer: Observer<List<Movie>>, error: Observer<Status>){
        callPopularMovies = services.getPopularMovies(apiKey, language, page)
        callPopularMovies.enqueue(object : Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                when(response.code()){
                    200 -> {
                        if (response.body()?.movies!!.isNotEmpty()) observer.onChanged(response.body()?.movies)
                        else error.onChanged(Status(404, "Sin peliculas encontradas"))
                    }
                    401 -> {
                        error.onChanged(Status(401, "Sin autorizacion"))
                    }
                    404 -> {
                        error.onChanged(Status(404, "No encontrado"))
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                error.onChanged(t.message?.let { error ->
                    Status(100, error)
                })
            }
        })
    }

    fun getTopRatedMovies(apiKey: String, language: String, page: Int, observer: Observer<List<Movie>>, error: Observer<Status>){
        callTopRatedMovies = services.getTopRatedMovies(apiKey, language, page)
        callTopRatedMovies.enqueue(object : Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                when(response.code()){
                    200 -> {
                        if (response.body()?.movies!!.isNotEmpty()) observer.onChanged(response.body()?.movies)
                        else error.onChanged(Status(404, "Sin peliculas encontradas"))
                    }
                    401 -> {
                        error.onChanged(Status(401, "Sin autorizacion"))
                    }
                    404 -> {
                        error.onChanged(Status(404, "No encontrado"))
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                error.onChanged(t.message?.let { error ->
                    Status(100, error)
                })
            }
        })
    }

    fun getMoviesBySearch(apiKey: String, language: String, query: String, page: Int, adult: Boolean, observer: Observer<List<Movie>>, error: Observer<Status>){
        callMoviesBySearch = services.getMoviesBySearch(apiKey, language, query, page, adult)
        callMoviesBySearch.enqueue(object : Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                when(response.code()){
                    200 -> {
                        if (response.body()?.movies!!.isNotEmpty()) observer.onChanged(response.body()?.movies)
                        else error.onChanged(Status(404, "Sin peliculas encontradas"))
                    }
                    401 -> {
                        error.onChanged(Status(401, "Sin autorizacion"))
                    }
                    404 -> {
                        error.onChanged(Status(404, "No encontrado"))
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                error.onChanged(t.message?.let { error ->
                    Status(100, error)
                })
            }
        })
    }

    fun getMoviesRecommedation(apiKey: String, language: String, page: Int, movieId: Int, observer: Observer<List<Movie>>, error: Observer<Status>){
        callMoviesRecommendation = services.getMoviesRecommendationByMovie(movieId, apiKey, language, page)
        callMoviesRecommendation.enqueue(object : Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                when(response.code()){
                    200 -> {
                        if (response.body()?.movies!!.isNotEmpty()) observer.onChanged(response.body()?.movies)
                        else error.onChanged(Status(404, "Sin peliculas encontradas"))
                    }
                    401 -> {
                        error.onChanged(Status(401, "Sin autorizacion"))
                    }
                    404 -> {
                        error.onChanged(Status(404, "No encontrado"))
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                error.onChanged(t.message?.let { error ->
                    Status(100, error)
                })
            }
        })
    }

    fun getVideoMovie(apiKey: String, language: String, movieId: Int, observer: Observer<VideoResult>, error: Observer<Status>){
        callVideo = services.getVideoMovie(movieId, apiKey, language)
        callVideo.enqueue(object : Callback<VideoResponse>{
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                when(response.code()){
                    200 -> {
                        if (response.body()?.videos?.isNotEmpty() == true) observer.onChanged(response.body()?.videos?.get(0))
                        else error.onChanged(Status(404, "Video unavailable"))
                    }
                    401 -> {
                        error.onChanged(Status(401, "Sin autorizacion"))
                    }
                    404 -> {
                        error.onChanged(Status(404, "No encontrado"))
                    }
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                error.onChanged(t.message?.let { error ->
                    Status(100, error)
                })
            }
        })
    }
}