package com.alan.heremovies.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alan.heremovies.R
import com.alan.heremovies.data.models.Movie
import com.alan.heremovies.sys.extensions.getProgressDrawable
import com.alan.heremovies.sys.extensions.loadImage
import com.alan.heremovies.sys.utils.DiffCallback

class MovieAdapterGrid(
    private val movieListener: OnClickMovieListener,
    private val context: Context
) : RecyclerView.Adapter<MovieAdapterGrid.HolderMovie>() {

    private val mMovies = mutableListOf<Movie>()

    fun addMovies(movies: List<Movie>){
        val diffCallbck = DiffCallback(mMovies, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallbck)
        mMovies.clear()
        mMovies.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovie {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_grid, parent, false)
        return HolderMovie(v)
    }

    override fun onBindViewHolder(holder: HolderMovie, position: Int) {

        mMovies[position].image?.let { holder.cover.loadImage(it, getProgressDrawable(context)) }

    }

    override fun getItemCount(): Int = mMovies.size

    inner class HolderMovie(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val card: CardView = itemView.findViewById(R.id.movieCard)
        val cover: ImageView = itemView.findViewById(R.id.coverGrid)

        init {
            card.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0?.id == R.id.movieCard){
                movieListener.onClickMovie(mMovies[adapterPosition], cover)
            }
        }
    }
}