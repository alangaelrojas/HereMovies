package com.alan.heremovies.data.datasource.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.alan.heremovies.data.datasource.database.Database
import com.alan.heremovies.data.models.Movie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

    private lateinit var database: Database
    private lateinit var moviesDao: MoviesDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                Database::class.java
        )
                .allowMainThreadQueries()
                .build()

        moviesDao = database.moviesDao()

    }

    @After
    fun terminate(){
        database.close()
    }

    @Test fun insertMovie() = runBlockingTest {
        val movie = Movie(
            true,
            "image",
            1,
            "en",
            "Spiderman",
            "Oh no, im superhero xD",
            9.0,
            "-",
            "2007-05-01",
            "Spiderman",
            false,
            99.0,
            1000,
            "popular",
            null
        )
        moviesDao.insertMovies(movie)

        val allMovies = moviesDao.getMovies("popular")

        assertThat(allMovies).contains(movie)

    }

    @Test fun getMoviesBySearch(){}

    @Test fun getMoviesRecommendaion(){}



}