package me.arjati.moviestreamingapp

import com.google.gson.annotations.SerializedName
import me.arjati.moviestreamingapp.MovieData.Movies
import me.arjati.moviestreamingapp.MovieData.ResultsItem

data class SearchData(
    @SerializedName("Search") val data: List<ResultsItem>,
    @SerializedName("totalResults") val totalData:Int,
    @SerializedName("Response") val res:String
)
