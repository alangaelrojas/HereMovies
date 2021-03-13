package com.alan.heremovies.ui.movies

import android.app.ActivityOptions
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build.*
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.heremovies.R
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.databinding.ActivityMoviesBinding
import com.alan.heremovies.sys.utils.broadcast_receiver.NetworkStateReceiver
import com.alan.heremovies.sys.utils.broadcast_receiver.OnNetworkChange
import com.alan.heremovies.ui.movie_detail.DetailMovieActivity
import com.alan.heremovies.ui.search_movies.DialogSearchFragment
import com.google.android.material.snackbar.Snackbar


class MoviesActivity : AppCompatActivity(), OnClickMovieListener, View.OnClickListener,
    OnNetworkChange {

    private lateinit var binding: ActivityMoviesBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var moviesAdapter: MovieAdapter

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = NetworkStateReceiver(this)
        registerBroadCastReceiver()

        moviesAdapter = MovieAdapter(this, this)

        setObservers()

        initViews()

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadCastReceiver()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.popular -> {
                viewModel.getPopularMovies()
                loadingMovies()

            }
            R.id.topRated -> {
                viewModel.getTopRatedMovies()
                loadingMovies()
            }
            R.id.fab_search -> {
                supportFragmentManager.let { fg ->
                    DialogSearchFragment().show(fg, "")
                }
            }
        }
    }

    override fun onClickMovie(movie: Movie, movieImageView: ImageView) {

        val intent = Intent(this, DetailMovieActivity::class.java).apply {
            putExtra("movie", movie)
        }
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName")

        startActivity(intent, options.toBundle())

    }

    private fun setObservers(){

        viewModel.popular.observe(this, this::popularMovies)
        viewModel.topRated.observe(this, this::topRatedMovies)
        viewModel.errorTopRated.observe(this) { error ->
            run {
                when(error.code){
                    100 -> {
                        emptyMovies(error.message, false)
                    }
                    401 -> {
                        emptyMovies(error.message, false)
                    }
                    404 -> {
                        emptyMovies(error.message, true)
                    }
                }
            }
        }

        viewModel.errorPopular.observe(this){ error -> run {
                when(error.code){
                    100 -> {
                        emptyMovies(error.message, false)
                    }
                    401 -> {
                        emptyMovies(error.message, false)
                    }
                    404 -> {
                        emptyMovies(error.message, true)
                    }
                }
            }
        }
    }

    private fun initViews(){
        //RecyclerView
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }

        binding.popular.isCheckable = true
        binding.topRated.isCheckable = true

        binding.popular.setOnClickListener(this)
        binding.topRated.setOnClickListener(this)
        binding.fabSearch.setOnClickListener(this)

        viewModel.getPopularMovies()

    }

    //region :: REFERENCES METHODS
    private fun popularMovies(movies: List<Movie>){
        binding.topRated.isChecked = false
        binding.popular.isChecked = true

        moviesAdapter.addPopularMovies(movies)

        loadedMovies()
    }

    private fun topRatedMovies(movies: List<Movie>){
        binding.popular.isChecked = false
        binding.topRated.isChecked = true

        moviesAdapter.addTopRated(movies)

        loadedMovies()
    }
    //endregion

    //region :: SET VIEWS
    private fun loadingMovies(){
        binding.shimmerMovies.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
        binding.lyEmpty.root.visibility = View.GONE
    }

    private fun loadedMovies(){
        binding.shimmerMovies.visibility = View.GONE
        binding.rvMovies.visibility = View.VISIBLE
        binding.lyEmpty.root.visibility = View.GONE
    }

    private fun emptyMovies(message: String, empty: Boolean){
        binding.lyEmpty.root.visibility = View.VISIBLE
        binding.lyEmpty.tvEmpty.text = message

        if (empty){
            binding.lyEmpty.lottieView.setAnimation("not_found_animation.json")
        } else {
            binding.lyEmpty.lottieView.setAnimation("error_screen.json")
        }
        binding.lyEmpty.lottieView.playAnimation()


        binding.shimmerMovies.visibility = View.GONE
        binding.rvMovies.visibility = View.GONE
    }
    //endregion

    //region :: PRIVATE METHODS
    private fun registerBroadCastReceiver(){
        if (VERSION.SDK_INT >= VERSION_CODES.N){
            registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }
    private fun unregisterBroadCastReceiver(){
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (i: IllegalArgumentException){
            i.printStackTrace()
        }
    }
    //endregion

    override fun hasInternet(hasConnection: Boolean) {
        if (hasConnection) {
            Snackbar.make(binding.root, "Yeih!! We are connected", Snackbar.LENGTH_LONG).show()
            viewModel.getPopularMovies()
        }
    }

}