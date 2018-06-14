package com.alvarenga.gamenews.gamenewsuca.api

import com.alvarenga.gamenews.gamenewsuca.api.models.News
import com.alvarenga.gamenews.gamenewsuca.api.models.Player
import retrofit2.Call
import retrofit2.http.*

interface GameNewsucaAPI {
    companion object {
        const val ENDPOINT = "http://gamenewsuca.herokuapp.com"
    }

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("user") username:String,
        @Field("password") password:String
    ): Call<String>

    @GET("/news")
    fun news(
            @Header("Authorization")
            autho:String
    ):Call<List<News>>

    @GET("/news/type/list")
    fun categories(@Header("Authorization") autho:String):Call<List<String>>

    @GET("/players")
    fun players(@Header("Authorization") autho:String):Call<List<Player>>

}
