package me.arjati.moviestreamingapp

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.arjati.moviestreamingapp.MovieData.ResultsItem

class MovieHolder(view: View, val eventHandling: (ResultsItem) -> Unit) : RecyclerView.ViewHolder(view) {

    val tvBannerTitle = view.findViewById<TextView>(R.id.tv_titleBanner)
    val ivBannerImg = view.findViewById<ImageView>(R.id.iv_imageBanner)

    val ivRecentImg = view.findViewById<ImageView>(R.id.iv_imageRecent)

    val tvRecomTitle = view.findViewById<TextView>(R.id.tv_titleRecom)
    val tvWatch = view.findViewById<TextView>(R.id.tv_watch)
    val tvReleaseDate = view.findViewById<TextView>(R.id.tv_releaseDate)
    val tvRate = view.findViewById<TextView>(R.id.tv_textRate)
    val ivRecomImg = view.findViewById<ImageView>(R.id.iv_imgRecom)

    val ivSearchImg = view.findViewById<ImageView>(R.id.iv_search)
    val tvSearch = view.findViewById<TextView>(R.id.tv_search)

    val imageBase = "https://image.tmdb.org/t/p/w500"

    @SuppressLint("ResourceType")
    fun bindViewBanner(movie: ResultsItem){
        tvBannerTitle.text = movie.title
        Picasso
            .get()
            .load(imageBase+movie.backdropPath)
            .into(ivBannerImg)

        itemView.setOnClickListener{ eventHandling(movie)}
    }

    @SuppressLint("ResourceType")
    fun bindViewRecent(movie: ResultsItem){
        Picasso
            .get()
            .load(imageBase+movie.posterPath)
            .into(ivRecentImg)

        itemView.setOnClickListener{ eventHandling(movie)}
    }

    @SuppressLint("ResourceType")
    fun bindViewRecom(movie: ResultsItem){
        tvRecomTitle.text = movie.title
        tvWatch.text = movie.popularity.toString()
        tvReleaseDate.text = movie.releaseDate
        tvRate.text = movie.voteAverage.toString()
        Picasso
            .get()
            .load(imageBase+movie.posterPath)
            .into(ivRecomImg)

        itemView.setOnClickListener{ eventHandling(movie)}
    }

    @SuppressLint("ResourceType")
    fun bindViewSearch(movie: ResultsItem){
        tvBannerTitle.text = movie.title
        Picasso
            .get()
            .load(imageBase+movie.posterPath)
            .into(ivSearchImg)

        itemView.setOnClickListener{ eventHandling(movie)}
    }
}