package me.arjati.moviestreamingapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import me.arjati.moviestreamingapp.DetailActivity
import me.arjati.moviestreamingapp.MovieAdapterSearch
import me.arjati.moviestreamingapp.MovieData.APIService
import me.arjati.moviestreamingapp.MovieData.Movies
import me.arjati.moviestreamingapp.MovieData.ResultsItem
import me.arjati.moviestreamingapp.R
import me.arjati.moviestreamingapp.RClient
import me.arjati.moviestreamingapp.databinding.FragmentDataBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager = GridLayoutManager(context, 2)

        val bundle = arguments
        val s = bundle?.getString("carimovie")


        RClient.instances.getMovieSearch(s.toString()).enqueue(object : retrofit2.Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val cekRes = response.body()?.results?.toString()

                if(cekRes == "True"){
                    response.body()?.let { list.addAll(it.results!!) }
//                    val adapter = MovieAdapterSearch(list)
                    binding.rvData.adapter = MovieAdapterSearch(list){
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("spiderman", it)
                        startActivity(intent)
                    }
                } else{
                    Toast.makeText(context, "Movie Not Found :(",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}