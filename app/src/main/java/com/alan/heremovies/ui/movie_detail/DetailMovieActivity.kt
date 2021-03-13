package com.alan.heremovies.ui.movie_detail

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.heremovies.R
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.databinding.ActivityDetailMovieBinding
import com.alan.heremovies.sys.extensions.getProgressDrawable
import com.alan.heremovies.sys.extensions.loadImage
import com.alan.heremovies.sys.utils.Constants
import com.alan.heremovies.ui.movies.MovieAdapterGrid
import com.alan.heremovies.ui.movies.OnClickMovieListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.*


class DetailMovieActivity : AppCompatActivity(), OnClickMovieListener,
    YouTubePlayer.OnInitializedListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel by viewModels<DetailMovieViewModel>()

    private lateinit var moviesAdapter: MovieAdapterGrid

    private var player: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)


        moviesAdapter = MovieAdapterGrid(this, this)

        val frag: YouTubePlayerSupportFragmentX? = supportFragmentManager.findFragmentById(R.id.ytVideoPlayer) as YouTubePlayerSupportFragmentX?
        frag?.initialize(Constants.YOUTUBE_API_KEY, this)

        setObservers()

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }

    private fun setObservers(){
        viewModel.recommendations.observe(this) { movies -> run {
            moviesAdapter.addMovies(movies)
            loadedMovies()
        }}
        viewModel.errorRecommendations.observe(this) { error -> run {
            emptyMovies()
        }}
        viewModel.movie.observe(this) { movie ->

            movie.posterPath?.let { binding.imageView.loadImage(it, getProgressDrawable(this)) }
            movie.image?.let { binding.detailMovieImg.loadImage(it, getProgressDrawable(this)) }

            binding.detailMovieTitle.text = movie?.title
            binding.detailMovieDesc.text = movie?.overview

            supportActionBar?.title = movie.title

            binding.lyRecommendations.rvRecommendations.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
                setHasFixedSize(true)
            }

            movie?.id?.let { id ->
                viewModel.getRecommendations(id)
                viewModel.getVideo(id)
                loadingMovies()
            }
        }

        viewModel.video.observe(this) { video ->
            binding.imageView.visibility = View.GONE
            player?.apply {
                setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                loadVideo(video.key)
                play()
            }
        }

        viewModel.errorVideo.observe(this) { error ->
            Snackbar.make(binding.root, error.message, Snackbar.LENGTH_LONG).show()
            binding.imageView.visibility = View.VISIBLE
        }
    }

    private fun initViews(){


        val movie: Movie? = intent.extras?.getParcelable("movie")
        movie?.let { viewModel.setMovie(it) }


    }

    override fun onClickMovie(movie: Movie, movieImageView: ImageView) {
        viewModel.setMovie(movie)
    }

    private fun loadingMovies(){
        binding.lyRecommendations.shimmerMovies.visibility = View.VISIBLE
        binding.lyRecommendations.rvRecommendations.visibility = View.GONE
    }

    private fun loadedMovies(){
        binding.lyRecommendations.shimmerMovies.visibility = View.GONE
        binding.lyRecommendations.rvRecommendations.visibility = View.VISIBLE
    }

    private fun emptyMovies(){
        binding.lyRecommendations.root.visibility = View.GONE
    }

    //region :: YOUTUBE INITIALIZATION
    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        p2: Boolean
    ) {

        player = youTubePlayer

        /*
        youTubePlayer?.apply {
            setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            loadVideo("xGbr9LOSbC0")
            play()
        }

         */

    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {}
    //endregion
}