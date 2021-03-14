package com.alan.heremovies.ui.detail_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alan.heremovies.ui.movie_detail.DetailMovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setup() {
        viewModel = DetailMovieViewModel()
    }


}