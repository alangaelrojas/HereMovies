package com.alan.heremovies.ui.movies

import android.widget.ImageView
import com.alan.heremovies.data.models.Movie

interface OnClickMovieListener {

    fun onClickMovie(movie: Movie, movieImageView: ImageView)
}