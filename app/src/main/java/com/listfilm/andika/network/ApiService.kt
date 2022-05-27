package com.listfilm.andika.network

import com.listfilm.andika.model.movie.GetMoviee

import com.listfilm.andika.model.update.UpdateResponse
import com.listfilm.andika.model.user.GetAllUserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("PopularMovie")
    fun getPopularMovie(): Call<List<GetMoviee>>

    @GET("NewMovieAndika")
    fun getNewMovie(): Call<List<GetMoviee>>

    @GET("RecMovieAndika")
    fun getRecMovie(): Call<List<GetMoviee>>

    @GET("UserAndika")
    fun getAllNewUser(): Call<List<GetAllUserItem>>

    @PUT("UserAndika/{id}")
    fun updateNewUser(
        @Body user : UpdateResponse, @Path("id") id : String
    ): Call<GetAllUserItem>


    @GET("UserAndika")
    fun Login(@Query("email") email : String) : Call<List<GetAllUserItem>>

    @POST("UserAndika")
    @FormUrlEncoded
    fun registerNew(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("complete_name") completename: String,
        @Field("address") address: String,
        @Field("dateofbirth") dateofbirth: String,
        @Field("image") image: String
        ): Call<GetAllUserItem>


}