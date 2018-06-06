package com.alvarenga.gamenews.gamenewsuca.api.models

import com.google.gson.annotations.SerializedName

class Player{
    private lateinit var avatar:String
    @SerializedName("_id")
    private lateinit var id:String
    private lateinit var name:String
    private lateinit var biografia:String
    private lateinit var game:String
}