package com.alvarenga.gamenews.gamenewsuca.api.models

import com.google.gson.annotations.SerializedName


class User{

    @SerializedName("_id")
    private lateinit var id:String

    @SerializedName("created_date")
    private lateinit var date:String

}