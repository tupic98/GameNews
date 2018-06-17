package com.alvarenga.gamenews.gamenewsuca.api.models

import com.google.gson.annotations.SerializedName

class Player{
    lateinit var avatar:String
    @SerializedName("_id")
    lateinit var id:String
    lateinit var name:String
    lateinit var biografia:String
    lateinit var game:String
}