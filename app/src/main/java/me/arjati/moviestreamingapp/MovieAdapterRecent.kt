package me.arjati.moviestreamingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.arjati.moviestreamingapp.MovieData.ResultsItem

class MovieAdapterRecent (private val dataset: List<ResultsItem>, val eventHandling: (ResultsItem) -> Unit) : RecyclerView.Adapter<MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_recent,parent,false), eventHandling
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindViewRecent(dataset[position])
        holder.itemView.setOnClickListener{
            eventHandling(dataset[position])
        }
    }

    override fun getItemCount(): Int = dataset.size
}