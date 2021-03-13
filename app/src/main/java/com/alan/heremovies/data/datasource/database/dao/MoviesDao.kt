package com.alan.heremovies.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.alan.heremovies.data.models.Movie


@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    fun insertMovies(article: Movie)

    @Query("select * from movie where type=:type order by popularity desc limit 100")
    fun getMovies(type: String): List<Movie>

    @Query("select * from movie where title like :title order by popularity desc limit 100")
    fun getMoviesBySearch(title: String): List<Movie>

    @Query("select * from movie where type=:type and recoId = :recoId order by popularity desc limit 100")
    fun getMoviesRecommendaion(type: String, recoId: Int): List<Movie>


}