package com.alan.heremovies.data.datasource.memory

import androidx.lifecycle.Observer
import com.alan.heremovies.MainApp
import com.alan.heremovies.data.datasource.database.dao.MoviesDao
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.sys.di.components.DaggerFrameworkComponent
import com.alan.heremovies.sys.di.modules.ContextModule
import com.alan.heremovies.sys.di.modules.DatabaseModule
import com.alan.heremovies.sys.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesMemoryDS {

    init {
        DaggerFrameworkComponent.builder()
            .contextModule(ContextModule(MainApp.getUtils().getContext()))
            .databaseModule(DatabaseModule())
            .build()
            .inject(this)
    }

    @Inject lateinit var moviesDao: MoviesDao

    fun insertMovies(movies: List<Movie>, type: String){
        CoroutineScope(Dispatchers.IO).launch {
            for (movie in movies) {
                Movie(
                        movie.adult,
                        movie.image,
                        movie.id,
                        movie.originalLanguage,
                        movie.originalTitle,
                        movie.overview,
                        movie.popularity,
                        movie.posterPath,
                        movie.releaseDate,
                        movie.title,
                        movie.video,
                        movie.voteAverage,
                        movie.voteCount,
                        type,
                        null
                ).also {
                    moviesDao.insertMovies(it)
                }
            }
        }
    }

    fun insertMoviesRecommendations(idReco: Int, movies: List<Movie>, type: String){
        CoroutineScope(Dispatchers.IO).launch {
            for (movie in movies) {
                Movie(
                        movie.adult,
                        movie.image,
                        movie.id,
                        movie.originalLanguage,
                        movie.originalTitle,
                        movie.overview,
                        movie.popularity,
                        movie.posterPath,
                        movie.releaseDate,
                        movie.title,
                        movie.video,
                        movie.voteAverage,
                        movie.voteCount,
                        type,
                        idReco
                ).also {
                    moviesDao.insertMovies(it)
                }
            }
        }
    }

    fun getMovies(type: String, observer: Observer<List<Movie>>, error: Observer<Status>){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = moviesDao.getMovies(type)
            withContext(Dispatchers.Main){
                if (movies.isEmpty()) error.onChanged(Status(404, "No se encontraron"))
                else observer.onChanged(movies)
            }
        }
    }

    fun getMoviesBySearch(title: String, observer: Observer<List<Movie>>, error: Observer<Status>){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = moviesDao.getMoviesBySearch("${title}%")
            withContext(Dispatchers.Main){
                if (movies.isEmpty()) error.onChanged(Status(404, "No se encontraron"))
                else observer.onChanged(movies)
            }
        }
    }

    fun getMoviesRecommendation(type: String, movieId: Int, observer: Observer<List<Movie>>, error: Observer<Status>){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = moviesDao.getMoviesRecommendaion(type, movieId)
            withContext(Dispatchers.Main){
                if (movies.isEmpty()) error.onChanged(Status(404, "No se encontraron"))
                else observer.onChanged(movies)
            }
        }
    }
}