package me.arjati.moviestreamingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import me.arjati.moviestreamingapp.MovieData.*
import me.arjati.moviestreamingapp.databinding.ActivityMainBinding
import me.arjati.moviestreamingapp.fragment.DataFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //auth
    private  lateinit var userEmail : String
    private lateinit var auth: FirebaseAuth

    //Recycler
    private lateinit var recyclerView : RecyclerView
    private lateinit var movieRecyclerView : MovieAdapterBanner

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        userEmail = getEmailFromPreference()

        findViewById<Button>(R.id.btn_logout).apply {
            setOnClickListener {
                logout()
            }
        }

        //search
        showFragment()

        //movie
        val rvBanner = findViewById<RecyclerView>(R.id.rv_banner)
        rvBanner.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBanner.setHasFixedSize(true)
        getMovieDataBanner{ movie: List<ResultsItem> ->
            rvBanner.adapter = MovieAdapterBanner(movie){
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("movie", it)
                startActivity(intent)
            }
        }

        val rvRecent = findViewById<RecyclerView>(R.id.rv_recentMovie)
        rvRecent.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecent.setHasFixedSize(true)
        getMovieDataRecent{ movie: List<ResultsItem> ->
            rvRecent.adapter = MovieAdapterRecent(movie){
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("movie", it)
                startActivity(intent)
            }
        }

        val rvRecom = findViewById<RecyclerView>(R.id.rv_recomMovie)
        rvRecom.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvRecom.setHasFixedSize(true)
        getMovieDataRecom { movie: List<ResultsItem> ->
            rvRecom.adapter = MovieAdapterRecom(movie){
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("movie", it)
                startActivity(intent)
            }
        }
    }


    private fun getMovieDataBanner(callback: (List<ResultsItem>) -> Unit){
        val apiServiceBanner = retrofitService().create(APIService::class.java)
        apiServiceBanner.getMovieListBanner().enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>){
                return callback(response.body()!!.results!!)
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {

            }
        })
    }

    private fun getMovieDataRecent(callback: (List<ResultsItem>) -> Unit){
        val apiServiceRecent = retrofitService().create(APIService::class.java)
        apiServiceRecent.getMovieListRecent().enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>){
                return callback(response.body()!!.results!!)
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {

            }
        })
    }

    private fun getMovieDataRecom(callback: (List<ResultsItem>) -> Unit){
        val apiServiceRecom = retrofitService().create(APIService::class.java)
        apiServiceRecom.getMovieListRecom().enqueue(object : Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                return callback(response.body()!!.results!!)
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private var retrofit: Retrofit? = null
    fun retrofitService(): Retrofit {
        if(retrofit==null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun getEmailFromPreference(): String{
        val sharePref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharePref.getString("email", "undefined")!!
    }

    private fun logout(){
        auth.signOut()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun showFragment(){
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = DataFragment()

        val textCari = binding.searchView.toString()
        val mBundle = Bundle()
        mBundle.putSerializable("carimovie", textCari.toString())
        mFragment.arguments = mBundle

        mFragmentTransaction.add(R.id.search_view, mFragment).commit()
    }

}

