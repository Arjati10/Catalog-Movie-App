package me.arjati.moviestreamingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import me.arjati.moviestreamingapp.MovieData.Movies
import me.arjati.moviestreamingapp.MovieData.ResultsItem
import me.arjati.moviestreamingapp.databinding.ActivityDetailBinding
import me.arjati.moviestreamingapp.databinding.FragmentDataBinding
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityDetailBinding
    var b:Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<ResultsItem> ("movie")

        val ivImage = findViewById<ImageView>(R.id.iv_imageDetail)
        val tvTitle = findViewById<TextView>(R.id.tv_titleDetail)
        val tvDesc = findViewById<TextView>(R.id.tv_descDetail)
        val tvRate = findViewById<TextView>(R.id.tv_rateDetail)
        val tvCalendar = findViewById<TextView>(R.id.tv_calendarDetail)
        val ivimgeps1 = findViewById<ImageView>(R.id.imgdetail1)
        val ivimgeps2 = findViewById<ImageView>(R.id.imgdetail2)

        val imageBase = "https://image.tmdb.org/t/p/w500"

        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnShare = findViewById<Button>(R.id.btn_share)

        tvTitle.text =  data?.title
        tvDesc.text = data?.overview
        tvRate.text = data?.voteAverage.toString()
        tvCalendar.text = data?.releaseDate
        Picasso
            .get()
            .load(imageBase+data?.backdropPath)
            .into(ivImage)
        Picasso
            .get()
            .load(imageBase+data?.posterPath)
            .into(ivimgeps1)
        Picasso
            .get()
            .load(imageBase+data?.backdropPath)
            .into(ivimgeps2)

        //Intent
        btnPlay.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/results?search_query="+data?.title))
            startActivity(intent)
        }

        btnShare.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://api.whatsapp.com/send?text=Ayo+Nonton+"+data?.title+"+https://www.youtube.com/results?search_query="+data?.title))
            startActivity(intent)
        }
    }
}