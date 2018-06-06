package com.alvarenga.gamenews.gamenewsuca.api

import com.alvarenga.gamenews.gamenewsuca.api.models.Login
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
    ): Call<Login>

}
