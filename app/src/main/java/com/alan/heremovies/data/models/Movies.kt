package com.alan.heremovies.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MoviesResponse (

    @SerializedName("page") val page : Int,
    @SerializedName("results") val movies : List<Movie>,
    @SerializedName("total_pages") val totalPages : Int,
    @SerializedName("total_results") val totalResults : Int
)

@Entity(tableName = "movie")
@Parcelize
data class Movie(

        @ColumnInfo(name = "adult")
        @SerializedName("adult") val adult : Boolean?,

        @ColumnInfo(name = "image")
        @SerializedName("backdrop_path") val image : String?,

        @PrimaryKey
        @SerializedName("id") val id : Int?,

        @ColumnInfo(name = "originalLanguage")
        @SerializedName("original_language") val originalLanguage : String?,

        @ColumnInfo(name = "originalTitle")
        @SerializedName("original_title") val originalTitle : String?,

        @ColumnInfo(name = "overview")
        @SerializedName("overview") val overview : String?,

        @ColumnInfo(name = "popularity")
        @SerializedName("popularity") val popularity : Double?,

        @ColumnInfo(name = "posterPath")
        @SerializedName("poster_path") val posterPath : String?,

        @ColumnInfo(name = "releaseDate")
        @SerializedName("release_date") val releaseDate : String?,

        @ColumnInfo(name = "title")
        @SerializedName("title") val title : String?,

        @ColumnInfo(name = "video")
        @SerializedName("video") val video : Boolean?,

        @ColumnInfo(name = "voteAverage")
        @SerializedName("vote_average") val voteAverage : Double?,

        @ColumnInfo(name = "voteCount")
        @SerializedName("vote_count") val voteCount : Int?,

        @ColumnInfo(name = "type")
        val type : String?,

        @ColumnInfo(name = "recoId")
        val idRecomendation : Int?


): Parcelable

data class VideoResponse (

        @SerializedName("id") val id : Int,
        @SerializedName("results") val videos : List<VideoResult>,
)

data class VideoResult(
        @SerializedName("id") val id: String, // "5c9b83b89251416b0cf835bf",
        @SerializedName("key") val key: String, // "se9n853lBNo",
        @SerializedName("name") val name: String, // "Fast Color Movie Trailer",
        @SerializedName("site") val site: String, // "YouTube"
)



