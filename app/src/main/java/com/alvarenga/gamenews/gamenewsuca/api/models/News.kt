package com.alvarenga.gamenews.gamenewsuca.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

class News{
    @SerializedName("_id")
    lateinit var id:String
    lateinit var title:String
    lateinit var body:String
    lateinit var game:String
    @SerializedName("create_date")
    lateinit var date:String
    lateinit var coverImage:String
    lateinit var description:String
}
