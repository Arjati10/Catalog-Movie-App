package me.arjati.moviestreamingapp.MovieData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/3/movie/popular?api_key=a51131a61364ce7248813f5b63d714c2")
    fun getMovieListBanner(): Call<Movies>
    @GET("/3/movie/now_playing?api_key=a51131a61364ce7248813f5b63d714c2")
    fun getMovieListRecent(): Call<Movies>
    @GET("/3/movie/top_rated?api_key=a51131a61364ce7248813f5b63d714c2")
    fun getMovieListRecom(): Call<Movies>
    @GET("/3/search/movie?api_key=a51131a61364ce7248813f5b63d714c2&language=en-US&query=")
    fun getMovieSearch(
        @Query("s") s:String
    ): Call<Movies>
//    @GET("/3/search/movie?api_key=a51131a61364ce7248813f5b63d714c2&language=en-US&query=")
//    fun getDetailMovieSearch(
//        @Query("i") s:String
//    ): Call<Movies>
}