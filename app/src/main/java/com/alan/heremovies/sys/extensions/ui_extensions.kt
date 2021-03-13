package com.alan.heremovies.sys.extensions

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alan.heremovies.R
import com.alan.heremovies.sys.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri:String, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.movie)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(Constants.IMAGES_BASE_URL + uri)
            .into(this)
}
